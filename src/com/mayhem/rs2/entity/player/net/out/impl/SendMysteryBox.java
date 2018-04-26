package com.mayhem.rs2.entity.player.net.out.impl;

import com.mayhem.core.network.StreamBuffer;
import com.mayhem.rs2.entity.player.net.Client;
import com.mayhem.rs2.entity.player.net.out.OutgoingPacket;



public class SendMysteryBox extends OutgoingPacket {
	
	private final int item;
	private final int amount;
	private final int frame;
	private final int slot;
	
	public SendMysteryBox(int item,int amount,int frame,int slot) {
		super();
		this.item = item;
		this.amount = amount;
		this.frame = frame;
		this.slot = slot;
	}

	@Override
	public void execute(Client client) {
		StreamBuffer.OutBuffer out = StreamBuffer.newOutBuffer(32);
		out.writeVariableShortPacketHeader(client.getEncryptor(), 34);
		out.writeShort(frame);
		out.writeByte(slot);
		if (item == 0) {
			out.writeShort(0);
			out.writeByte(0);
		} else {
			out.writeShort(item + 1);
			if (amount > 254) {
				out.writeByte(255);
				out.writeInt(amount);
			} else {
				out.writeByte(amount);
			}
		}
		out.finishVariableShortPacketHeader();
		client.send(out.getBuffer());
	}

	@Override
	public int getOpcode() {
		return 34;
	}
}
/**
public void mysteryBoxItemOnInterface(int item, int amount , int frame, int slot) {
if (c.getOutStream() != null && c != null) {
		c.getOutStream().createFrameVarSizeWord(34);
		c.getOutStream().writeWord(frame);
		c.getOutStream().writeByte(slot);
		c.getOutStream().writeWord(item + 1);
		c.getOutStream().writeByte(255);
		c.getOutStream().writeDWord(amount);
		c.getOutStream().endFrameVarSizeWord();
	}
}
*/