package org.softcits.pc.core.mapper;

import java.util.List;
import org.apache.ibatis.annotations.Param;
import org.softcits.pc.core.model.MbgComputer;
import org.softcits.pc.core.model.MbgComputerExample;

public interface MbgComputerMapper {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table computer
     *
     * @mbg.generated Sun Apr 22 13:31:26 CST 2018
     */
    long countByExample(MbgComputerExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table computer
     *
     * @mbg.generated Sun Apr 22 13:31:26 CST 2018
     */
    int deleteByExample(MbgComputerExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table computer
     *
     * @mbg.generated Sun Apr 22 13:31:26 CST 2018
     */
    int deleteByPrimaryKey(Integer id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table computer
     *
     * @mbg.generated Sun Apr 22 13:31:26 CST 2018
     */
    int insert(MbgComputer record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table computer
     *
     * @mbg.generated Sun Apr 22 13:31:26 CST 2018
     */
    int insertSelective(MbgComputer record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table computer
     *
     * @mbg.generated Sun Apr 22 13:31:26 CST 2018
     */
    List<MbgComputer> selectByExample(MbgComputerExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table computer
     *
     * @mbg.generated Sun Apr 22 13:31:26 CST 2018
     */
    MbgComputer selectByPrimaryKey(Integer id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table computer
     *
     * @mbg.generated Sun Apr 22 13:31:26 CST 2018
     */
    int updateByExampleSelective(@Param("record") MbgComputer record, @Param("example") MbgComputerExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table computer
     *
     * @mbg.generated Sun Apr 22 13:31:26 CST 2018
     */
    int updateByExample(@Param("record") MbgComputer record, @Param("example") MbgComputerExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table computer
     *
     * @mbg.generated Sun Apr 22 13:31:26 CST 2018
     */
    int updateByPrimaryKeySelective(MbgComputer record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table computer
     *
     * @mbg.generated Sun Apr 22 13:31:26 CST 2018
     */
    int updateByPrimaryKey(MbgComputer record);
}