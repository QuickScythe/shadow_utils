package me.quickscythe.shadowutils.commands.executors;

import io.papermc.paper.command.brigadier.BasicCommand;
import io.papermc.paper.command.brigadier.CommandSourceStack;
import org.jetbrains.annotations.NotNull;

public class UpdateCommand implements BasicCommand {
    @Override
    public void execute(@NotNull CommandSourceStack stack, @NotNull String[] strings) {
        stack.getSender().sendPlainMessage("test");
    }
}
