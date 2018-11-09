package com.block.demo;

import java.util.ArrayList;
import java.util.Date;

public class Block {
	public String hash;
	public String previousHash;
	private String data; // 数据        
	private long timeStamp; // 时间戳   
	private int nonce;// 增加一个随机数   
	public ArrayList<Transaction> transactions = new ArrayList<Transaction>();
	
	//Block Constructor.  
	public Block(String previousHash ) {
		this.previousHash = previousHash;
		this.timeStamp = new Date().getTime();
		
		this.hash = calculateHash(); //Making sure we do this after we set the other values.
	}

	// 区块构造函数        
	/*
	 * public Block(String data, String previousHash) { this.data = data;
	 * this.previousHash = previousHash; this.timeStamp = new Date().getTime(); }
	 */
	public Block(String data, String previousHash) {
		this.data = data;
		this.previousHash = previousHash;
		this.timeStamp = new Date().getTime();
		this.hash = calculateHash(); // 确保hash值的来源
	}

	public String calculateHash() {
		String calculatedhash = StringUtil
				.applySha256(previousHash + Long.toString(timeStamp) + Integer.toString(nonce) + data);
		return calculatedhash;
	}

	public void mineBlock(int difficulty) {
		// 创建一个string值由难度的位数来决定
		String target = new String(new char[difficulty]).replace('\0', '0');
		while (!hash.substring(0, difficulty).equals(target)) {
			nonce++;
			hash = calculateHash();
		}
		System.out.println("Block Mined!!! : " + hash);
	}

	// Add transactions to this block
	public boolean addTransaction(Transaction transaction) {
		// process transaction and check if valid, unless block is genesis block then
		// ignore.
		if (transaction == null)
			return false;
		if ((previousHash != "0")) {
			if ((transaction.processTransaction() != true)) {
				System.out.println("Transaction failed to process. Discarded.");
				return false;
			}
		}

		transactions.add(transaction);
		System.out.println("Transaction Successfully added to Block");
		return true;
	}

}
