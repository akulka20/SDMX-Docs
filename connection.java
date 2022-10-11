package com.Login;


import java.sql.Connection;
import java.sql.*;

public class connection {

  //  private static int //num;

	public static String getTableData(String tableName) throws SQLException, ClassNotFoundException {
        Connection con = null;
        Statement st = null;
        ResultSet rs = null;
        try {
            Class.forName("oracle.jdbc.driver.OracleDriver");
            con = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521/ORCL", "fred", "flintstone");
            Statement stmt = con.createStatement();
            rs = stmt.executeQuery("SELECT * FROM " + tableName);

            ResultSetMetaData rsmd = rs.getMetaData();
            int colCount = rsmd.getColumnCount();

            StringBuilder b = new StringBuilder("<?xml version=\"1.0\" encoding=\"UTF-8\"?>\r\n"
            		+ "<mes:StructureSpecificData xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xmlns:mes=\"http://www.sdmx.org/resources/sdmxml/schemas/v2_1/message\" xmlns:ss=\"http://www.sdmx.org/resources/sdmxml/schemas/v2_1/data/structurespecific\" xmlns:ns1=\"urn:sdmx:org.sdmx.infomodel.datastructure.DataStructure=CIMS_RETURN:LNA(3.0):ObsLevelDim:AllDimensions\" xmlns:com=\"http://www.sdmx.org/resources/sdmxml/schemas/v2_1/common\" xsi:schemaLocation=\"http://www.sdmx.org/resources/sdmxml/schemas/v2_1/message https://registry.sdmx.org/schemas/v2_1/SDMXMessage.xsd\">\r\n"
            		+ "	<mes:Header>\r\n"
            		+ "		<mes:ID>CIMS</mes:ID>\r\n"
            		+ "		<mes:Test>false</mes:Test>\r\n"
            		+ "		<mes:Prepared>2021-10-05T09:28:21</mes:Prepared>\r\n"
            		+ "		<mes:Sender id=\"999\"/>\r\n"
            		+ "		<mes:Receiver id=\"Not_supplied\"/>\r\n"
            		+ "		<mes:Structure structureID=\"LNA\" namespace=\"urn:sdmx:org.sdmx.infomodel.datastructure.DataStructure=CIMS_RETURN:LNA(3.0)\" dimensionAtObservation=\"AllDimensions\">\r\n"
            		+ "			<com:Structure>\r\n"
            		+ "				<Ref agencyID=\"CIMS_RETURN\" id=\"LNA\" version=\"3.0\" class=\"DataStructure\"/>\r\n"
            		+ "			</com:Structure>\r\n"
            		+ "		</mes:Structure>\r\n"
            		+ "	</mes:Header>\n");

            //num = 1;
            while(rs.next()) {
               System.out.println(rs.getInt(1));
                b.append("<StructureSpecificData>");
                for (int i = 1; i <= colCount; i++) {
                    String columnName = rsmd.getColumnName(i);
                    //System.out.println(columnName);
                    b.append('<').append(columnName).append('>');
                    b.append(rs.getObject(i));
                    b.append("</").append(columnName).append('>');
                }
                b.append("</StructureSpecificData>\n");
            }
            

//            int num = 1;
//            while (rs.next()) {
//                b.append("<StructureSpecificData>");
//                b.append("<num>").append(num++).append("</num>");
//                for (int i = 1; i <= colCount; i++) {
//                    String columnName = rsmd.getColumnName(i);
//                    b.append('<').append(columnName).append('>');
//                    b.append(rs.getObject(i));
//                    b.append("</").append(columnName).append('>');
//                }
//                b.append("</StructureSpecificData>\n");
//            }
            b.append("</XML>");
            return b.toString();
        } catch (SQLException e) {
            throw e;
        } catch (ClassNotFoundException e) {
            throw e;
        } finally {
            if (rs != null)
                try {
                    rs.close();
                } catch (SQLException e) {
                }
            if (st != null)
                try {
                    st.close();
                } catch (SQLException e) {
                }
            if (con != null)
                try {
                    con.close();
                } catch (SQLException e) {
                }
        }
    }

    public static void main(String[] args) throws Exception {
        String str = getTableData("DIMENSIONTYPEMASTER");
        System.out.println(str);
    }
}