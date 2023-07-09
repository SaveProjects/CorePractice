package fr.edminecoreteam.corepractice;

import com.google.common.io.ByteArrayDataInput;
import com.google.common.io.ByteStreams;
import fr.edminecoreteam.corepractice.blocs.BlocsListener;
import fr.edminecoreteam.corepractice.blocs.BlocsToWorld;
import fr.edminecoreteam.corepractice.data.hashmap.elo.PlayerEloDataManager;
import fr.edminecoreteam.corepractice.data.hashmap.game.GameMap;
import fr.edminecoreteam.corepractice.data.hashmap.ranked.RankedLoseDataManager;
import fr.edminecoreteam.corepractice.data.hashmap.ranked.RankedPlayedDataManager;
import fr.edminecoreteam.corepractice.data.hashmap.ranked.RankedWinDataManager;
import fr.edminecoreteam.corepractice.data.hashmap.unranked.UnrankedLoseDataManager;
import fr.edminecoreteam.corepractice.data.hashmap.unranked.UnrankedPlayedDataManager;
import fr.edminecoreteam.corepractice.data.hashmap.unranked.UnrankedWinDataManager;
import fr.edminecoreteam.corepractice.edorm.MySQL;
import fr.edminecoreteam.corepractice.edorm.PlayerJoinQuitSQL;
import fr.edminecoreteam.corepractice.edorm.SQLState;
import fr.edminecoreteam.corepractice.edorm.SQLTasks;
import fr.edminecoreteam.corepractice.gui.ProfileGui;
import fr.edminecoreteam.corepractice.gui.RankedGui;
import fr.edminecoreteam.corepractice.gui.UnrankedGui;
import fr.edminecoreteam.corepractice.listeners.*;
import fr.edminecoreteam.corepractice.matchduels.*;
import fr.edminecoreteam.corepractice.matchduels.timer.TimerDataManager;
import fr.edminecoreteam.corepractice.matchmaking.FoundGame;
import fr.edminecoreteam.corepractice.matchmaking.GameCheck;
import fr.edminecoreteam.corepractice.matchmaking.WhatIsGame;
import fr.edminecoreteam.corepractice.scoreboard.JoinScoreboardEvent;
import fr.edminecoreteam.corepractice.scoreboard.LeaveScoreboardEvent;
import fr.edminecoreteam.corepractice.scoreboard.ScoreboardManager;
import fr.edminecoreteam.corepractice.utils.SoupUtils;
import fr.edminecoreteam.corepractice.utils.TablistRankJoinListener;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.plugin.messaging.PluginMessageListener;

import java.util.AbstractList;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;

public class Core extends JavaPlugin implements PluginMessageListener
{

    private static Core instance;
    private ScheduledExecutorService executorMonoThread;
    private ScheduledExecutorService scheduledExecutorService;

    private ScoreboardManager scoreboardManager;

    public MySQL database;
    public SQLState sqlState;

    private final List<Player> inLobby;
    private final List<Player> inEditor;
    private final List<Player> inWaiting;

    private final List<Player> inPreDuel;
    private final List<Player> inDuel;
    private final List<Player> inEndDuel;

    private final GameCheck gameCheck;
    private final TypeGame typeGame;
    private final GameID gameID;
    private final WorldName worldName;
    private final MatchOppenant matchOppenant;
    private final TimerDataManager timerData;

    private final BlocsToWorld blocsToWorld;

    private final UnrankedPlayedDataManager unrankedPlayedDataManager;
    private final UnrankedWinDataManager unrankedWinDataManager;
    private final UnrankedLoseDataManager unrankedLoseDataManager;

    private final RankedPlayedDataManager rankedPlayedDataManager;
    private final RankedWinDataManager rankedWinDataManager;
    private final RankedLoseDataManager rankedLoseDataManager;

    private final PlayerEloDataManager playerEloDataManager;

    private final WhatIsGame gameIs;

    private final GameMap gameMap;

    public Core() {

        inLobby = new ArrayList<Player>();
        inEditor = new ArrayList<Player>();
        inWaiting = new ArrayList<Player>();
        inDuel = new ArrayList<Player>();
        inPreDuel = new ArrayList<Player>();
        inEndDuel = new ArrayList<Player>();

        gameCheck = new GameCheck();
        typeGame = new TypeGame();
        gameID = new GameID();
        worldName = new WorldName();
        matchOppenant = new MatchOppenant();
        timerData = new TimerDataManager();
        blocsToWorld = new BlocsToWorld();
        gameIs = new WhatIsGame();

        unrankedPlayedDataManager = new UnrankedPlayedDataManager();
        unrankedWinDataManager = new UnrankedWinDataManager();
        unrankedLoseDataManager = new UnrankedLoseDataManager();

        rankedPlayedDataManager = new RankedPlayedDataManager();
        rankedWinDataManager = new RankedWinDataManager();
        rankedLoseDataManager = new RankedLoseDataManager();

        playerEloDataManager = new PlayerEloDataManager();
        gameMap = new GameMap();
    }

    public List<Player> getInLobby() { return this.inLobby; }
    public List<Player> getInEditor() { return this.inEditor; }
    public List<Player> getInWaiting() { return this.inWaiting; }

    public List<Player> getInPreDuel() { return this.inPreDuel; }
    public List<Player> getInDuel() { return this.inDuel; }
    public List<Player> getInEndDuel() { return this.inEndDuel; }

    @Override
    public void onEnable() {
        instance = this;

        saveDefaultConfig();

        this.getServer().getMessenger().registerOutgoingPluginChannel(this, "BungeeCord");
        this.getServer().getMessenger().registerIncomingPluginChannel(this, "BungeeCord", this);

        MySQLConnect();
        ScoreboardManager();
        loadListeners();
    }

    @Override
    public void onDisable() {
        MySQLDisconnect();

        this.getServer().getMessenger().unregisterOutgoingPluginChannel(this);
        this.getServer().getMessenger().unregisterIncomingPluginChannel(this);
    }

    /*
     * Méthode de connexion au serveur SQL.
     *
     * "jdbc:mysql://", "45.140.165.235", "22728-database", "22728-database", "S5bV5su4p9"
     */
    public void MySQLConnect()
    {
        instance = this;

        (this.database = new MySQL(instance, "jdbc:mysql://", "45.140.165.235", "22728-database", "22728-database", "S5bV5su4p9")).connexion();

        database.creatingTablePractice();
        updateColumnsPractice();
    }

    /*
     * Méthode de déconnexion au serveur SQL.
     */
    public void MySQLDisconnect()
    {
        database.deconnexion();
    }

    /*
     * Méthode de statut de l'instance MySQL
     * State List: DISCONECTED 0, CONECTED 1.
     */
    public void setSQLState(SQLState sqlState)
    {
        this.sqlState = sqlState;
    }
    public boolean isSQLState(SQLState sqlState)
    {
        return this.sqlState == sqlState;
    }

    /*
     * Méthode de chargement des listeners.
     */
    private void loadListeners()
    {
        instance = this;

        Bukkit.getPluginManager().registerEvents(new PlayerJoinQuitSQL(), this);

        Bukkit.getPluginManager().registerEvents(new JoinEvent(), this);
        Bukkit.getPluginManager().registerEvents(new QuitEvent(), this);
        Bukkit.getPluginManager().registerEvents(new DoubleJumpListener(), this);
        Bukkit.getPluginManager().registerEvents(new EventListeners(), this);
        Bukkit.getPluginManager().registerEvents(new ItemListeners(), this);
        Bukkit.getPluginManager().registerEvents(new GameListeners(), this);
        Bukkit.getPluginManager().registerEvents(new BlocsListener(), this);
        Bukkit.getPluginManager().registerEvents(new SoupUtils(), this);
        Bukkit.getPluginManager().registerEvents(new TablistRankJoinListener(), this);
        Bukkit.getPluginManager().registerEvents(new PlayerChatListener(), this);

        Bukkit.getPluginManager().registerEvents(new UnrankedGui(), this);
        Bukkit.getPluginManager().registerEvents(new RankedGui(), this);

        Bukkit.getPluginManager().registerEvents(new ProfileGui(), this);
        FoundGame.start();
    }

    /*
     * Méthode de chargement des scoreboards.
     */
    private void ScoreboardManager() {
        instance = this;

        Bukkit.getPluginManager().registerEvents(new JoinScoreboardEvent(), this);
        Bukkit.getPluginManager().registerEvents(new LeaveScoreboardEvent(), this);

        scheduledExecutorService = Executors.newScheduledThreadPool(16);
        executorMonoThread = Executors.newScheduledThreadPool(1);
        scoreboardManager = new ScoreboardManager();
    }

    @Override
    public void onPluginMessageReceived(String channel, Player player, byte[] message)
    {
        if (!channel.equals("BungeeCord"))
        {
            return;
        }
        ByteArrayDataInput in = ByteStreams.newDataInput(message);
        String subchannel = in.readUTF();
        if (subchannel.equals("SomeSubChannel"))
        {
            // Use the code sample in the 'Response' sections below to read
            // the data.
        }
    }

    public void updateColumnsPractice()
    {
        for (String modes : this.getConfig().getConfigurationSection("kits.1vs1").getKeys(false))
        {
            database.updateColumnsPractice(this.getConfig().getString("kits.1vs1." + modes + ".id"));
        }
    }

    public void addTime(Player player, int amount) {
        timerData.addTime(player.getUniqueId(), amount);
    }

    public void removeTime(Player player, int amount) { timerData.removeTime(player.getUniqueId(), amount); }

    public void resetTime(Player player) { timerData.resetTime(player.getUniqueId()); }

    public int getTime(Player player) {
        return timerData.getPlayerData(player.getUniqueId()).getTime();
    }

    public UnrankedPlayedDataManager getUnrankedPlayedDataManager() { return this.unrankedPlayedDataManager; }
    public UnrankedWinDataManager getUnrankedWinDataManager() { return this.unrankedWinDataManager; }
    public UnrankedLoseDataManager getUnrankedLoseDataManager() { return this.unrankedLoseDataManager; }

    public RankedPlayedDataManager getRankedPlayedDataManager() { return this.rankedPlayedDataManager; }
    public RankedWinDataManager getRankedWinDataManager() { return this.rankedWinDataManager; }
    public RankedLoseDataManager getRankedLoseDataManager() { return this.rankedLoseDataManager; }

    public PlayerEloDataManager getPlayerEloDataManager() { return this.playerEloDataManager; }

    public GameMap getGameMap() { return this.gameMap; }

    /*
     * Méthode de retournement de l'instance.
     */
    public GameCheck getGameCheck() { return this.gameCheck; }
    public TypeGame getGameType() { return this.typeGame; }
    public GameID getGameID() { return this.gameID; }
    public WorldName getWorldName() { return this.worldName; }
    public MatchOppenant getMatchOppenant() { return this.matchOppenant; }

    public BlocsToWorld getBlocsToWorld() { return this.blocsToWorld; }

    public WhatIsGame getGameIs() { return this.gameIs; }

    public ScoreboardManager getScoreboardManager() {
        return this.scoreboardManager;
    }

    public ScheduledExecutorService getExecutorMonoThread() {
        return this.executorMonoThread;
    }

    public ScheduledExecutorService getScheduledExecutorService() {
        return this.scheduledExecutorService;
    }
    public static Core getInstance() { return Core.instance; }
    public static Plugin getPlugin() { return Core.getPlugin(); }
}
