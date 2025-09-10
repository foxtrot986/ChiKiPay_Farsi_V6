param([string]$ResPath = "D:\R&D_Project_2025\Project_ChiKiPay_2025\ChiKiPay_Farsi_V6\app\src\main\res")

$Densities = [ordered]@{ mdpi=24; hdpi=36; xhdpi=48; xxhdpi=72; xxxhdpi=96 }

function Get-WebPSize($Path) {
  if (!(Test-Path $Path)) { return $null }
  $fs=[IO.File]::OpenRead($Path); $br=New-Object IO.BinaryReader($fs)
  try{
    $riff=-join($br.ReadChars(4)); if($riff -ne 'RIFF'){return $null}
    [void]$br.ReadUInt32(); $webp=-join($br.ReadChars(4)); if($webp -ne 'WEBP'){return $null}
    while($fs.Position -lt $fs.Length){
      $fcc=-join($br.ReadChars(4)); if($fcc.Length -lt 4){break}
      $cs=$br.ReadUInt32(); $st=$fs.Position
      switch($fcc){
        'VP8X' { [void]$br.ReadByte(); [void]$br.ReadBytes(3)
                 $w0=$br.ReadByte();$w1=$br.ReadByte();$w2=$br.ReadByte()
                 $h0=$br.ReadByte();$h1=$br.ReadByte();$h2=$br.ReadByte()
                 return [pscustomobject]@{Width=1+($w0+($w1 -shl 8)+($w2 -shl 16));Height=1+($h0+($h1 -shl 8)+($h2 -shl 16))} }
        'VP8L' { [void]$br.ReadByte(); $val=$br.ReadUInt32()
                 return [pscustomobject]@{Width=1+($val -band 0x3FFF);Height=1+(($val -shr 14) -band 0x3FFF)} }
        'VP8 ' { $data=$br.ReadBytes([int]$cs)
                 for($i=0;$i -le $data.Length-7;$i++){
                   if($data[$i]-eq 0x9D -and $data[$i+1]-eq 0x01 -and $data[$i+2]-eq 0x2A){
                     $w=[BitConverter]::ToUInt16($data,$i+3); $h=[BitConverter]::ToUInt16($data,$i+5)
                     return [pscustomobject]@{Width=($w -band 0x3FFF); Height=($h -band 0x3FFF)}
                   } } }
      }
      $fs.Position=$st+$cs+($cs%2)
    }
  } finally { $br.Close(); $fs.Close() }
  return $null
}

if (!(Test-Path $ResPath)) { throw "res/ not found: $ResPath" }

$icons = Get-ChildItem $ResPath -Recurse -Filter ic_*.webp -File |
         Select-Object -Expand BaseName -Unique

$report = @()
foreach($n in $icons){
  foreach($kv in $Densities.GetEnumerator()){
    $d  = $kv.Key; $px = $kv.Value
    $p  = Join-Path $ResPath ("drawable-{0}\{1}.webp" -f $d,$n)
    if(!(Test-Path $p)){
      $report += [pscustomobject]@{Icon=$n;Density=$d;ExpectedPx=$px;Path=$p;Status='MISSING';Width=$null;Height=$null;Bytes=0}
    } else {
      $len=(Get-Item $p).Length
      $dim=Get-WebPSize $p
      if($dim -and $dim.Width -eq $px -and $dim.Height -eq $px){
        $report += [pscustomobject]@{Icon=$n;Density=$d;ExpectedPx=$px;Path=$p;Status='OK';Width=$dim.Width;Height=$dim.Height;Bytes=$len}
      } else {
        $w = if($dim){$dim.Width}else{$null}; $h = if($dim){$dim.Height}else{$null}
        $report += [pscustomobject]@{Icon=$n;Density=$d;ExpectedPx=$px;Path=$p;Status="MISMATCH ($w x $h)";Width=$w;Height=$h;Bytes=$len}
      }
    }
  }
}

$root = Split-Path (Split-Path (Split-Path $ResPath -Parent) -Parent) -Parent
$csv  = Join-Path $root ("ChiKiPay_Icon_Verify_DST_{0}.csv" -f (Get-Date -Format 'yyyyMMdd_HHmmss'))
$report | Sort-Object Icon,Density | Export-Csv -NoTypeInformation -Encoding UTF8 $csv

$ok  = ($report | Where-Object Status -eq 'OK').Count
$bad = ($report | Where-Object Status -ne 'OK').Count
$need= $report.Count

"`n===== ChiKiPay Icons Check ====="
"Expected: $need  |  OK: $ok  |  Issues: $bad"
"CSV: $csv"
"=================================`n" | Write-Host

if($bad -gt 0){ exit 2 } else { exit 0 }
