package fr.kappacite.blockshuffle.objects.comparator;

import fr.kappacite.blockshuffle.objects.player.ShufflePlayer;

import java.util.Comparator;

public class PointComparator implements Comparator<ShufflePlayer> {

    @Override
    public int compare(ShufflePlayer o1, ShufflePlayer o2) {
        return o1.getPoint().compareTo(o2.getPoint());
    }

    @Override
    public boolean equals(Object obj) {
        return false;
    }

}
