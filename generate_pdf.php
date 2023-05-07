<?php
//include connection file 
include_once("connection2.php");
include_once('fpdf/fpdf.php');


class PDF extends FPDF
{
// Page header
function Header()
{
    // Logo
    $this->SetFont('Arial','B',13);
    // Move to the right
    $this->Cell(80);
    // Title
    $this->Cell(80,10,'Employee List',1,0,'C');
    // Line break
    $this->Ln(20);
}
 
// Page footer
function Footer()
{
    // Position at 1.5 cm from bottom
    $this->SetY(-15);
    // Arial italic 8
    $this->SetFont('Arial','I',8);
    // Page number
    $this->Cell(0,10,'Page '.$this->PageNo().'/{nb}',0,0,'C');
}
}
 
$db = new dbObj();
$connString =  $db->getConnstring();
$display_heading = array('id'=>'ID', 'user_id'=> 'user_id', 'm_id'=> 'm_id','crop_name'=> 'crop_name','variety'=> 'variety','season'=> 'season','data_status'=> 'data_status','timestamp'=> 'timestamp');
 
$result = mysqli_query($connString, "SELECT id, user_id, m_id, crop_name,variety,season,data_status,timestamp FROM crop") or die("database error:". mysqli_error($connString));
$header = mysqli_query($connString, "SHOW columns FROM crop");
 
$pdf = new PDF();
//header
$pdf->AddPage();
//foter page
$pdf->AliasNbPages();
$pdf->SetFont('Arial','B',12);
foreach($header as $heading) {
$pdf->Cell(40,12,$display_heading[$heading['Field']],1);
}
foreach($result as $row) {
$pdf->Ln();
foreach($row as $column)
$pdf->Cell(40,12,$column,1);
}
$pdf->Output();
?>
?>