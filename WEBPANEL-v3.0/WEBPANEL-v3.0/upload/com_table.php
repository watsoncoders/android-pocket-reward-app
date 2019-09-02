<?php

 include_once 'connect_database.php';

	$connect    = new mysqli($host, $user, $pass,$database) or die("Error : ".mysql_error());
	
?>&nbsp;
<?php 
		
		// create array variable to store data from database
		$data = array();
		
			$sql_query = "SELECT rid, request_from, dev_name, dev_man, gift_name, req_amount, points_used, date, username
					FROM Completed
					ORDER BY rid DESC";
		
		
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
				// $stmt_paging ->bind_param('ss', $from, $offset);
				
			// Execute query
			$stmt_paging ->execute();
			// store result 
			$stmt_paging ->store_result();
			$stmt_paging->bind_result($data['rid'], 
					$data['request_from'], 
					$data['dev_name'],
					$data['dev_man'],
$data['gift_name'], 					$data['req_amount'], $data['points_used'], $data['date'], $data['username'] 
					);
					
					
			// for paging purpose
			$total_records_paging = $total_records; 
		}

		// if no data on database show "No Reservation is Available"
		if($total_records_paging == 0){
	
	?>
	<h1> No Records to Show !!
		
	</h1>
	<hr />
	<?php 
		// otherwise, show data
		}else{
			// $row_number = $from + 1;
	?>

                  <table id="datatable-buttons" class="table table-striped table-bordered">
                    <thead>
                      <tr>
                        
<th>No. </th>
<th>User Name</th>
<th>Requested email/mobile </th>
<th>Gift Name</th>
<th>Amount</th>
<th>Points Used</th>
<th>Device Name</th>
<th>Model No.</th>
<th>Date</th>
<th>Action</th>
                      </tr>
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
					<td><?php echo $data['request_from'];?></td>
							<td><?php echo $data['gift_name'];?></td>
						<td><?php echo $data['req_amount']; ?></td>
							<td><?php echo $data['points_used'];?></td>
							<td><?php echo $data['dev_man'];?></td>
					<td><?php echo $data['dev_name'];?></td> 
			<td><?php echo $data['date'];?></td>
                       
<td> <a href="tracker.php?user=<?php echo $data['username']; ?>" class="btn btn-primary btn-xs"><i class="fa fa-bar-chart-o"></i> Track Activity </a>



<a href="#" class="btn btn-danger btn-xs" data-toggle="modal" data-target=".bs-example-modal-sm<?php echo $data['rid'];?>"><i class="fa fa-trash-o"></i> Delete Record</a>


                <!-- Small modal -->

<div class="modal fade bs-example-modal-sm<?php echo $data['rid'];?>" tabindex="-1" role="dialog" aria-hidden="true">
                  <div class="modal-dialog modal-sm<?php echo $data['rid'];?>">
                    <div class="modal-content">

                      <div class="modal-header">
                        <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">×</span>
                        </button>
                        <h4 class="modal-title" id="myModalLabel2"> || Pocket </h4>
                      </div>
                      <div class="modal-body">
                        <h4>Confirm Delete?</h4>
                        <p>Do you really want to delete the Record ??.</p>
                      <div class="modal-footer">
                        <button type="button" onclick="window.location.href='completed.php'" class="btn btn-default" data-dismiss="modal">Cancel</button>
                        
<button type="button" onclick="window.location.href='confirm_delete_complete.php?id=<?php echo $data['rid'];?>
'" class="btn btn-primary"> Delete !</button>

                      </div>

                    </div>
                  </div>
                </div>
                </div>

                <!-- /modals -->
              </div>

</td>
                      </tr>
						<?php 
						} 
					}
				?>

                    </tbody>
                  </table>

<?php 
	$stmt->close();
?>