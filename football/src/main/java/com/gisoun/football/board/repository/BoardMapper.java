package com.gisoun.football.board.repository;

import java.util.List;

import com.gisoun.football.board.domain.BoardVO;
import com.gisoun.football.board.domain.ReplyVO;

public interface BoardMapper {
    /**
     * View Board List
     * 
     * @param vo
     * @return
     * @throws Exception
     */
    public List<BoardVO> selectBoardList(BoardVO vo) throws Exception;

    /**
     * View Count Of Board List
     * 
     * @param vo
     * @return
     * @throws Exception
     */
    public int selectBoardListCount(BoardVO vo) throws Exception;

    /**
     * View Board Detail
     * 
     * @param vo
     * @return
     * @throws Exception
     */
    public BoardVO selectBoardDetail(BoardVO vo) throws Exception;

    /**
     * Insert Board
     * 
     * @param vo
     * @return
     * @throws Exception
     */
    public int insertBoard(BoardVO vo) throws Exception;

    /**
     * Update Board
     * 
     * @param vo
     * @return
     * @throws Exception
     */
    public int updateBoard(BoardVO vo) throws Exception;

    /**
     * Delete Board
     * 
     * @param vo
     * @return
     * @throws Exception
     */
    public int deleteBoard(BoardVO vo) throws Exception;

    /**
     * Increase View Count
     * 
     * @param vo
     * @throws Exception
     */
    public void increaseViewCount(BoardVO vo) throws Exception;

    /**
     * View Reply List
     * 
     * @param vo
     * @return
     * @throws Exception
     */
    public List<ReplyVO> selectReplyList(ReplyVO vo) throws Exception;

    /**
     * Insert Reply
     * 
     * @param vo
     * @return
     * @throws Exception
     */
    public int insertReply(ReplyVO vo) throws Exception;

    /**
     * Update Reply
     * 
     * @param vo
     * @return
     * @throws Exception
     */
    public int updateReply(ReplyVO vo) throws Exception;

    /**
     * Delete Reply
     * 
     * @param vo
     * @return
     * @throws Exception
     */
    public int deleteReply(ReplyVO vo) throws Exception;
}
