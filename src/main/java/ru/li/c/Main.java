package ru.li.c;

import static jdk.tools.jlink.internal.plugins.PluginsResourceBundle.getMessage;

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Main {
    public static void main(String[] args) {
        //TIP Press <shortcut actionId="ShowIntentionActions"/> with your caret at the highlighted text
        // to see how IntelliJ IDEA suggests fixing it.
        System.out.println(getMessage());
        }
        private static String getMessage () {
            return "Hello qa,guru";
    }
}