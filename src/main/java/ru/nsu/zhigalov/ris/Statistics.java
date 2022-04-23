package ru.nsu.zhigalov.ris;

import java.util.*;
import java.util.stream.Collectors;

public class Statistics {
    private Map<String, Set<String>> userEditsMap;
    private final Map<String, Integer> userNodesMap;
    private Map<String, Integer> keyNodeAmountMap;

    public void addUserEdit(String username, String changeset) {
        userNodesMap.merge(username, 1, Integer::sum);
        userEditsMap
                .computeIfAbsent(username, __ -> new HashSet<>())
                .add(changeset);
    }

    public void addKeyNodeAmount(String key) {
        keyNodeAmountMap.merge(key, 1, Integer::sum);
    }

    public Map<String, Set<String>> getUserEditsMap() {
        return userEditsMap;
    }

    public void setUserEditsMap(Map<String, Set<String>> userEditsMap) {
        this.userEditsMap = userEditsMap;
    }

    public Map<String, Integer> getKeyNodeAmountMap() {
        return keyNodeAmountMap;
    }

    public void setKeyNodeAmountMap(Map<String, Integer> keyNodeAmountMap) {
        this.keyNodeAmountMap = keyNodeAmountMap;
    }

    public Statistics(Map<String, Set<String>> userEditsMap, Map<String, Integer> keyNodeAmountMap) {
        this.userEditsMap = userEditsMap;
        this.keyNodeAmountMap = keyNodeAmountMap;
        this.userNodesMap = new HashMap<>();
    }

    public String formatUserEditsMap() {
        return userEditsMap
                .entrySet()
                .stream()
                .map(entry -> Map.entry(entry.getKey(), entry.getValue().size()))
                .sorted(Comparator.comparingInt(Map.Entry::getValue))
                .map(entry ->
                        String.format(
                                "Username: %s - changes: %d",
                                entry.getKey(),
                                entry.getValue()
                        )
                )
                .collect(Collectors.joining("\n"));
    }

    @SuppressWarnings("ConstantConditions")
    private static String formatStringIntMap(Map<String, Integer> map, String formatString) {
        return map
                .entrySet()
                .stream()
                .sorted(Comparator.comparingInt(Map.Entry::getValue))
                .map(entry ->
                        String.format(
                                formatString,
                                entry.getKey(),
                                entry.getValue(),
                                entry.getValue() == 1 ? "" : "s"
                        )
                )
                .collect(Collectors.joining("\n"));
    }

    public String formatKeyNodeAmountMap() {
        return formatStringIntMap(keyNodeAmountMap, "Key name: %s - appears %d time%s");
    }

    public String formatUserNodesMap() {
        return formatStringIntMap(userNodesMap, "username: %s - has %d node%s");
    }

    @Override
    public String toString() {
        String separator = "\n--------------\n";
        return "User - edits: \n" + formatUserEditsMap() + separator + formatKeyNodeAmountMap() + separator + formatUserNodesMap();
    }

}
