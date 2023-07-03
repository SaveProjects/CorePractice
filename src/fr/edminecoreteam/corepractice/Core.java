package fr.edminecoreteam.corepractice;

import com.google.common.io.ByteArrayDataInput;
import com.google.common.io.ByteStreams;
import fr.edminecoreteam.corepractice.edorm.MySQL;
import fr.edminecoreteam.corepractice.edorm.SQLState;
import fr.edminecoreteam.corepractice.edorm.SQLTasks;
import fr.edminecoreteam.corepractice.listeners.EventListeners;
import fr.edminecoreteam.corepractice.listeners.ItemListeners;
import fr.edminecoreteam.corepractice.listeners.JoinEvent;
import fr.edminecoreteam.corepractice.listeners.QuitEvent;
import fr.edminecoreteam.corepractice.scoreboard.JoinScoreboardEvent;
import fr.edminecoreteam.corepractice.scoreboard.LeaveScoreboardEvent;
import fr.edminecoreteam.corepractice.scoreboard.ScoreboardManager;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
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

    private List<Player> inLobby;
    private List<Player> inEditor;

    public Core() {
        inLobby = new ArrayList<Player>();
        inEditor = new ArrayList<Player>();
    }

    public List<Player> getInLobby() { return this.inLobby; }
    public List<Player> getInEditor() { return this.inEditor; }

    @Override
    public void onEnable() {
        instance = this;

        saveDefaultConfig();

        this.getServer().getMessenger().registerOutgoingPluginChannel(this, "BungeeCord");
        this.getServer().getMessenger().registerIncomingPluginChannel(this, "BungeeCord", this::onPluginMessageReceived);

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

        Bukkit.getPluginManager().registerEvents(new JoinEvent(), this);
        Bukkit.getPluginManager().registerEvents(new QuitEvent(), this);
        Bukkit.getPluginManager().registerEvents(new EventListeners(), this);
        Bukkit.getPluginManager().registerEvents(new ItemListeners(), this);
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

    /*
     * Méthode de retournement de l'instance.
     */
    public ScoreboardManager getScoreboardManager() {
        return this.scoreboardManager;
    }

    public ScheduledExecutorService getExecutorMonoThread() {
        return this.executorMonoThread;
    }

    public ScheduledExecutorService getScheduledExecutorService() {
        return this.scheduledExecutorService;
    }
    public static Core getInstance() { return Core.getInstance(); }
    public static Plugin getPlugin() { return Core.getPlugin(); }
}
