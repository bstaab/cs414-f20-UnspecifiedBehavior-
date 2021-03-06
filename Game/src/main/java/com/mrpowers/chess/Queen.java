package com.mrpowers.chess;

import com.mrpowers.exceptions.IllegalPositionException;

import java.util.ArrayList;


public class Queen extends ChessPiece {

	public Queen(ChessBoard board, Color color) {
		super(board, color);

		if (this.color == Color.WHITE) {
			this.fenChar = 'Q';
		} else {
			this.fenChar = 'q';
		}
	}

	@Override
	public String toString() {
		if (this.color == Color.WHITE) {
			return "\u2655";
		} else {
			return "\u265B";
		}
	}

	@Override
	public ArrayList<String> legalMoves() {
		ArrayList<String> moves = new ArrayList<String>();
		
		String position = getPosition();
		int row = this.row + 1;
		int newRow = row;
		char col = position.charAt(0);
		char newCol;
		String move = "";
		
		ChessPiece piece;
		Color attackColor = Color.BLACK;
		if (color == Color.BLACK) {
		attackColor = Color.WHITE;			
		}
		
		// Get location of portals
		String whitePortalLocation = board.getPortalLocation(Color.WHITE);
		String blackPortalLocation = board.getPortalLocation(Color.BLACK);
		ChessPiece onWhitePortal = null;
		ChessPiece onBlackPortal = null;
		
		try{
			// Get the piece on top of the portals
			if (onBoard(whitePortalLocation)) {
				onWhitePortal = board.getPiece(whitePortalLocation);
			}
			if (onBoard(blackPortalLocation)) {
				onBlackPortal = board.getPiece(blackPortalLocation);
			}
			
			// Move down and up
			for (int rowDir = -1; rowDir <= 1; ++rowDir) {
				for(int colDir = -1; colDir <= 1; ++colDir) {
					if (rowDir == 0 && colDir == 0) {
						continue;
					}
					row = this.row + 1;
					newRow = row + rowDir;
					col = position.charAt(0);
					newCol = (char)(col + colDir);
					move = "" + newCol + newRow;
					while(onBoard(move)) {
						piece = board.getPiece(move);
						if(piece == null) {
							if (move.equals(whitePortalLocation)) {
								if (onBlackPortal == null) {
									moves.add(blackPortalLocation);
								} else {
									if (onBlackPortal.color == attackColor) {
										moves.add(blackPortalLocation);
									} else {
										moves.add(move);
									}
									break;
								} 
							} else if (move.equals(blackPortalLocation)) {
								if (onWhitePortal == null) {
									moves.add(whitePortalLocation);
								} else {
									if (onWhitePortal.color == attackColor) {
										moves.add(whitePortalLocation);
									} else {
										moves.add(move);
									}
									break;
								}
							} else {
								moves.add(move);
							}
						} else {
							if (piece.color == attackColor){
								moves.add(move);
							}
							break;
						}
						
						move = moves.get(moves.size() - 1);
		 				newCol = (char)(move.charAt(0) + colDir);
		 				newRow = move.charAt(1) - '0' + rowDir;
		 				move = "" + newCol + newRow;
					}
				}
			}
						
		} catch (IllegalPositionException e) {
			System.out.println("Illegal position for a Bishop");
		}
		
		return moves;
	}
	
	
	/*
	@Override
	// The queen can be moved any number of unoccupied squares 
	// in a straight line vertically, horizontally
	public ArrayList<String> legalMoves2() {
		ArrayList<String> moves = new ArrayList<String>();

		String position = getPosition();
		int row = this.row + 1;
		char col = position.charAt(0);
		String move = "";
		int newRow = row;
		char newCol;
		
		try{
			// Move down
			newRow = row - 1;
			move = "" + col + newRow;
			while(onBoard(move)) {
				if(board.getPiece(move) == null) {
					moves.add(move);
				} else {
					if(color != board.getPiece(move).color) {
						moves.add(move);
					} 
					break;
				}
				newRow -= 1;
				move = "" + col + newRow;
			}
						
			// Move up
			newRow = row + 1;
			move = "" + col + newRow;
			while(onBoard(move)) {	
				if(board.getPiece(move) == null)
				{
					moves.add(move);
				} else {
					if(color != board.getPiece(move).color)	{
						moves.add(move);
					} 					
					break;
				}
				newRow += 1;
				move = "" + col + newRow;
			}
			
			// Move left
			newCol	 = (char) (col - 1);
			move = "" + newCol + row;
			while(onBoard(move)) {
				if(board.getPiece(move) == null)
				{
					moves.add(move);
				} else {
					if(color != board.getPiece(move).color)	{
						moves.add(move);
					} 					
					break;
				}
				newCol -= 1;
				move = "" + newCol + row;
			}
			
			// Move right
			col = position.charAt(0);
			newCol = (char) (col + 1);
			move = "" + newCol + row;
			while(onBoard(move)) {
				if(board.getPiece(move) == null)
				{
					moves.add(move);
				} else {
					if(color != board.getPiece(move).color)	{
						moves.add(move);
					} 					
					break;
				}
				newCol += 1;
				move = "" + newCol + row;
			}
			
			// Move lower-left
			col = position.charAt(0);
			newCol = (char) (col - 1);
			newRow = row - 1;
			move = "" + newCol + newRow;
			while(onBoard(move)) {
				if(board.getPiece(move) == null)
				{
					moves.add(move);
				} else {
					if(color != board.getPiece(move).color)	{
						moves.add(move);
					} 					
					break;
				}
				newCol -= 1;
				newRow -= 1;
				move = "" + newCol + newRow;
			}
			
			// Move lower-right
			col = position.charAt(0);
			newCol = (char) (col + 1);
			newRow = row - 1;
			move = "" + newCol + newRow;
			while(onBoard(move)) {
				if(board.getPiece(move) == null)
				{
					moves.add(move);
				} else {
					if(color != board.getPiece(move).color)	{
						moves.add(move);
					} 					
					break;
				}
				newCol += 1;
				newRow -= 1;
				move = "" + newCol + newRow;
			}
			
			// Move upper-right
			col = position.charAt(0);
			newCol = (char) (col + 1);
			newRow = row + 1;
			move = "" + newCol + newRow;
			while(onBoard(move)) {
				if(board.getPiece(move) == null)
				{
					moves.add(move);
				} else {
					if(color != board.getPiece(move).color)	{
						moves.add(move);
					} 					
					break;
				}
				newCol += 1;
				newRow += 1;
				move = "" + newCol + newRow;
			}
			
			// Move upper-left
			col = position.charAt(0);
			newCol = (char) (col - 1);
			newRow = row + 1;
			move = "" + newCol + newRow;
			while(onBoard(move)) {
				if(board.getPiece(move) == null)
				{
					moves.add(move);
				} else {
					if(color != board.getPiece(move).color)	{
						moves.add(move);
					} 					
					break;
				}
				newCol -= 1;
				newRow += 1;
				move = "" + newCol + newRow;
			}
			
		}catch(IllegalPositionException e) {
			System.out.println("Invalid position for a Rook");
		}
		
		return moves;
	}
	*/
}
