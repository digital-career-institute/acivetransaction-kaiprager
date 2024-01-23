# find out current active trasaction

  private static void updateQuery() throws SQLException {
        String sql ...
        }
    }

    private static boolean isCurrentActiveTransaction() throws SQLException {
       

        String sql = "SELECT \n" +
                     "    COUNT(1) AS count\n" +
                     "FROM\n" +
                     "    INFORMATION_SCHEMA.INNODB_TRX\n" +
                     "WHERE\n" +
                     "    trx_mysql_thread_id = CONNECTION_ID()";

       
}
    


