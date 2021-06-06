<?php 
class DB_Connect {
 
   // constructor
    function __construct() {
		
	}
 
    // destructor
    function __destruct() {
        $this->close();
    }
 
   
    public function connect() {
        require_once 'db_config.php';
        
        $con = mysqli_connect(DB_HOST, DB_USER, DB_PASSWORD,DB_DATABASE);
     
 
        return $con;
    }
 
    
    public function close() {
        mysqli_close($con);
    }
} 
?>