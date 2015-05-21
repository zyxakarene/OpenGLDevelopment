package utils.exceptions;

import java.io.IOException;
import java.util.logging.FileHandler;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;
import utils.constants.GameConstants;

public class UncaughtExceptionHandlerImpl implements Thread.UncaughtExceptionHandler
{

    private static final Logger LOG = GameConstants.LOGGER; //Logger.getLogger(UncaughtExceptionHandlerImpl.class.getName());

    public UncaughtExceptionHandlerImpl() throws IOException
    {
        FileHandler handler = new FileHandler("log/logFile.txt", true);
        handler.setFormatter(new SimpleFormatter());
        LOG.addHandler(handler);
    }

    @Override
    public void uncaughtException(Thread thread, Throwable exception)
    {
        String msg = String.format("An uncaught exception was thrown by thread '%s'", thread.getName());
        LOG.log(Level.SEVERE, msg, exception);
    }
}
