package com.drazisil.mailit;

import org.bukkit.entity.Player;

import java.util.ArrayList;

public class MailboxManager {
    private final ArrayList<PlayerMailbox> mailboxes = new ArrayList<>();

    public PlayerMailbox getMailbox(Player player) {
        for (PlayerMailbox mailbox:mailboxes) {
            if (mailbox.getOwner() == player) return mailbox;
        }
        return null;
    }

    public void addMailboxes(PlayerMailbox mailbox) {
        this.mailboxes.add(mailbox);
    }

    public boolean hasMailbox(Player player) {

        for (PlayerMailbox mailbox:mailboxes) {
            if (mailbox.getOwner() == player) return true;
        }
        return false;
    }
}
