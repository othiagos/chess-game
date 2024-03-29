package chess.pieces;

import boardgame.Board;
import boardgame.Position;
import chess.ChessMatch;
import chess.ChessPiece;

public class King extends ChessPiece {

	ChessMatch chessMatch;

	public King(Board board, Color color, ChessMatch chessmatch) {
		super(board, color);
		this.chessMatch = chessmatch;
	}

	private boolean canMove(Position position) {
		ChessPiece p = (ChessPiece) getBoard().piece(position);
		return p == null || p.getColor() != getColor();
	}

	private boolean testRookCastling(Position position) {
		ChessPiece p = (ChessPiece) getBoard().piece(position);
		return p != null && p instanceof Rook && p.getColor() == getColor() && p.getMoveCount() == 0;
	}

	private boolean testCanMoveKing(Position p) {
		boolean[][] opp = chessMatch.getOpponentPossibleMoves();
		if (opp[p.getRow()][p.getColumn()] == true) {
			return false;
		}
		return true;
	}

	@Override
	public boolean[][] possibleMoves() {
		boolean[][] mat = new boolean[getBoard().getRows()][getBoard().getColumns()];
		Position p = new Position(0, 0);

		// above
		p.setValues(position.getRow() - 1, position.getColumn());
		if (getBoard().positionExists(p) && canMove(p) && testCanMoveKing(p)) {
				mat[p.getRow()][p.getColumn()] = true;
		}

		// below
		p.setValues(position.getRow() + 1, position.getColumn());
		if (getBoard().positionExists(p) && canMove(p) && testCanMoveKing(p)) {
				mat[p.getRow()][p.getColumn()] = true;
		}

		// left
		p.setValues(position.getRow(), position.getColumn() - 1);
		if (getBoard().positionExists(p) && canMove(p) && testCanMoveKing(p)) {
				mat[p.getRow()][p.getColumn()] = true;
		}

		// right
		p.setValues(position.getRow(), position.getColumn() + 1);
		if (getBoard().positionExists(p) && canMove(p) && testCanMoveKing(p)) {
				mat[p.getRow()][p.getColumn()] = true;
		}

		// nw
		p.setValues(position.getRow() - 1, position.getColumn() - 1);
		if (getBoard().positionExists(p) && canMove(p) && testCanMoveKing(p)) {
				mat[p.getRow()][p.getColumn()] = true;
		}

		// ne
		p.setValues(position.getRow() - 1, position.getColumn() + 1);
		if (getBoard().positionExists(p) && canMove(p) && testCanMoveKing(p)) {
				mat[p.getRow()][p.getColumn()] = true;
		}

		// sw
		p.setValues(position.getRow() + 1, position.getColumn() - 1);
		if (getBoard().positionExists(p) && canMove(p) && testCanMoveKing(p)) {
				mat[p.getRow()][p.getColumn()] = true;
		}

		// se
		p.setValues(position.getRow() + 1, position.getColumn() + 1);
		if (getBoard().positionExists(p) && canMove(p) && testCanMoveKing(p)) {
				mat[p.getRow()][p.getColumn()] = true;
		}

		// Castling
		if (getMoveCount() == 0 && !chessMatch.getCheck()) {
			// King Side
			Position posT1 = new Position(position.getRow(), position.getColumn() + 3);
			if (testRookCastling(posT1)) {
				Position p1 = new Position(position.getRow(), position.getColumn() + 1);
				Position p2 = new Position(position.getRow(), position.getColumn() + 2);
				if (getBoard().piece(p1) == null && getBoard().piece(p2) == null && testCanMoveKing(p1)
						&& testCanMoveKing(p2)) {
					mat[position.getRow()][position.getColumn() + 2] = true;
				}
			}
			// Queen Side
			Position posT2 = new Position(position.getRow(), position.getColumn() - 4);
			if (testRookCastling(posT2)) {
				Position p1 = new Position(position.getRow(), position.getColumn() - 1);
				Position p2 = new Position(position.getRow(), position.getColumn() - 2);
				Position p3 = new Position(position.getRow(), position.getColumn() - 3);
				if (getBoard().piece(p1) == null && getBoard().piece(p2) == null && getBoard().piece(p3) == null
						&& testCanMoveKing(p1) && testCanMoveKing(p2) && testCanMoveKing(p3)) {
					mat[position.getRow()][position.getColumn() - 2] = true;
				}
			}
		}
		return mat;
	}

	@Override
	public String toString() {
		return "R";
	}
}
