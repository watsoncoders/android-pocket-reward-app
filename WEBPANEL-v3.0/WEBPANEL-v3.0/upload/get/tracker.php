<?php


include '../connect_database.php'; 

if(isset($_GET['user'])){
				$user = $_GET['user'];
			
// echo $user;

		// create array variable to store data from database
		$data = array();
		
			$sql_query = "SELECT username, points, type, date
					FROM tracker WHERE username = '$user'";
		
		
		$stmt = $connect->stmt_init();
		if($stmt->prepare($sql_query)) {	
			// Bind your variables to replace the ?s
			
			// Execute query
			$stmt->execute();
			// store result 
			$stmt->store_result();

			// get total records
			$total_records = $stmt->num_rows;
		}
		
		
		$stmt_paging = $connect->stmt_init();
		if($stmt_paging ->prepare($sql_query)) {
			// Bind your variables to replace the ?s
				$stmt_paging ->bind_param('ss', $from, $offset);
				
			// Execute query
			$stmt_paging ->execute();
			// store result 
			$stmt_paging ->store_result();
			$stmt_paging->bind_result($data['date'], 
					$data['username'], 
					$data['type'],
					$data['points']);
					
					
			// for paging purpose
			$total_records_paging = $total_records; 
		}

		// if no data on database show "No Reservation is Available"
		if($total_records_paging == 0){
	
	?>
	<h1> This User Has No Activity !!
		
	</h1>
	<hr />
	<?php 
		// otherwise, show data
		}else{
			$row_number = $from + 1;
	?>

                  <table id="datatable-buttons" class="table table-striped table-bordered">
                    <thead>
                      <tr>
                        
<th>No. </th>
<th>User Name</th>
<th>Type</th>
<th>Date</th>
<th>points</th>       </tr>
                    </thead>

                    <tbody>

				
<?php 

$count = 0;
					while ($stmt_paging->fetch()){ 

    $count+=1;
?>
                      <tr>
			
<td><?php echo $count;?></td>
							<td><?php echo $data['username'];?></td>
							<td><?php echo $data['type'];?></td>
							<td><?php echo $data['date'];?></td>
						<td><?php echo $data['points']; ?></td>
                       
                      </tr>
						<?php 
						} 
					}
}else{

        echo '404 - Not Found <br/>';
        echo 'the Requested URL is not found on this server ';
			}
				?>

                    </tbody>
                  </table>


<?php 
	$stmt->close();
?>