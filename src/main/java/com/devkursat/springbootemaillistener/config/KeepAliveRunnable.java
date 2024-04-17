package com.devkursat.springbootemaillistener.config;

import com.sun.mail.imap.IMAPFolder;

public class KeepAliveRunnable implements Runnable {
    private static final long KEEP_ALIVE_FREQ = 300000; // 5 minutes
    private final IMAPFolder folder;
    public KeepAliveRunnable(IMAPFolder folder) {
        this.folder = folder;
    }

    @Override
    public void run() {
        while (!Thread.interrupted()) {
            try {
                Thread.sleep(KEEP_ALIVE_FREQ);

                // Perform a NOOP to keep the connection alive
                folder.doCommand(protocol -> {
                    protocol.simpleCommand("NOOP", null);
                    return null;
                });
            } catch (Exception e) {
                System.err.println("Unexpected exception while keeping alive the IDLE connection");
            }
        }
    }
}
