package edu.uga.smallsat;

import java.nio.ByteBuffer;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

import org.yamcs.TmPacket;
import org.yamcs.YConfiguration;
import org.yamcs.tctm.AbstractPacketPreprocessor;
import org.yamcs.utils.TimeEncoding;

public class SPOCPacketPreprocessor extends AbstractPacketPreprocessor {

    private Map<Integer, AtomicInteger> seqCounts = new HashMap<>();

    public SPOCPacketPreprocessor(String yamcsInstance) {
        super(yamcsInstance, YConfiguration.emptyConfig());
    }

    @Override
    public TmPacket process(TmPacket packet) {
        // TODO: Packet preprocessing, checksum, etc
        // Our custom packets don't include a secundary header with time information.
        
        byte[] bytes = packet.getPacket();
        int apidseqcount = ByteBuffer.wrap(bytes).getInt(0);
        
        // Use Yamcs-local time instead.
        packet.setGenerationTime(TimeEncoding.getWallclockTime());
        // Use the full 32-bits, so that both APID and the count are included.
        // Yamcs uses this attribute to uniquely identify the packet (together with the gentime)
        packet.setSequenceCount(apidseqcount);

        return packet;
    }
}
