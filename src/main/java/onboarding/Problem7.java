package onboarding;

import java.util.*;

public class Problem7 {
    public static List<String> solution(String user, List<List<String>> friends, List<String> visitors) {
        List<String> answer = new ArrayList<>();

        Map<String, Set<String>> friendshipGraph = new HashMap<>();
        initFriendshipGraph(friends, friendshipGraph);

        Map<String, Integer> visitorInfo = new HashMap<>();
        initVisitorInfo(visitors, visitorInfo, user, friendshipGraph);

        Map<String, Integer> friendOfFriend = new HashMap<>();
        findFriendOfFriend(user, friendshipGraph, friendOfFriend);

        return answer;
    }

    /**
     * Function for friendshipGraph that represent all friendship as a graph
     * */
    private static void initFriendshipGraph(List<List<String>> friends, Map<String, Set<String>> friendshipGraph) {
        for (List<String> each : friends) {
            String person1 = each.get(0);
            String person2 = each.get(1);

            if (!friendshipGraph.containsKey(person1)) {
                friendshipGraph.put(person1, new HashSet<String>());
            }

            if (!friendshipGraph.containsKey(person2)) {
                friendshipGraph.put(person2, new HashSet<String>());
            }

            friendshipGraph.get(person1).add(person2);
            friendshipGraph.get(person2).add(person1);
        }
    }

    /**
     * Function for visitorInfo that mapping visitor name and visit nums
     * */
    private static void initVisitorInfo(List<String> visitors, Map<String, Integer> visitorInfo,
                                        String user, Map<String, Set<String>> friendshipGraph) {
        for (String visitor : visitors) {
            if (friendshipGraph.get(user).contains(visitor)) {
                continue;
            }
            if (visitorInfo.containsKey(visitor)) {
                visitorInfo.put(visitor, visitorInfo.get(visitor)+1);
            } else {
                visitorInfo.put(visitor, 1);
            }
        }
    }

    /**
     * Function for finding friends of friends who are not user's friends
     * */
    private static void findFriendOfFriend(String user, Map<String, Set<String>> friendshipGraph,
                                           Map<String, Integer> friendOfFriend) {
        Map<String, Integer> checkSet = new HashMap<>();
        checkSet.put(user,0);
        Queue<String> queue = new LinkedList<>();
        queue.add(user);
        String person;
        int value;
        while (!queue.isEmpty()) {
            person = queue.remove();
            value = checkSet.get(person);
            if (value == 2) {
                break;
            }
            for (String next : friendshipGraph.get(person)) {
                if (value == 0) {
                    if (!checkSet.containsKey(next)) {
                        checkSet.put(next, 1);
                        queue.add(next);
                    }
                } else if (value == 1) {
                    if (!checkSet.containsKey(next)) {
                        checkSet.put(next, 2);
                        friendOfFriend.put(next, 1);
                        queue.add(next);
                    } else {
                        if (!next.equals(user)) {
                            friendOfFriend.put(next, friendOfFriend.get(next)+1);
                        }
                    }
                }
            }
        }
    }
}
