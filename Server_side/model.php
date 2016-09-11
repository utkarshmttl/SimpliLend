<?php

class Model extends CI_Model{

	public function getDetails(){
		
		$query="Select * from simplilend";
		$retvalue=$this->db->query($query);
		return $retvalue->result();
	}
	
	public function register($name,$username,$password){
		
		$query="Insert into simplilend (name, username, password) Values ('$name', '$username', '$password')";
		$this->db->query($query);
		return $this->db->query("SELECT LAST_INSERT_ID();")->result();
		
	}

}
?>