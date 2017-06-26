package maxdistructo.droidbot2.background;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

    public class PlayerList {
        int numPlayers;
        Object[] playerList;

        public PlayerList() {
            this.numPlayers = 0;
            this.playerList = new Object[1];
        }

        public PlayerList(int players) {
            this.numPlayers = players - 1;
            this.playerList = new Object[this.numPlayers];
        }

        public static List<Object> outputList(PlayerList a) {
            return Arrays.stream(a.playerList).collect(Collectors.toList());
        }


        public static Object[] outputArray(PlayerList a) {
            return a.playerList;
        }


        public static void addToList(PlayerList a, String player) {
            int i = 0;
            while (i <= a.numPlayers) {
                if (a.playerList[i] == null) {
                    a.playerList[i] = (player);
                    i = a.numPlayers + 1;
                }
                else {
                }

            }
        }


        public static void removeFromList(PlayerList a, String player) {
            int i = 0;
            while (i <= a.numPlayers) {
                if (a.playerList[i].equals(player)) {
                    a.playerList[i] = null;
                    i = a.numPlayers + 1;
                } else {
                }

            }
        }


        public static int countPlayers(PlayerList a) {
            int i = 0;
            int players = 0;
            while (i <= a.numPlayers) {
                if (!a.playerList[i].equals(null)) {
                    players++;
                } else {
                }
            }
            return players;
        }

    }
