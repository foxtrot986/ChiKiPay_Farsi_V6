param([string]$ResPath = "D:\R&D_Project_2025\Project_ChiKiPay_2025\ChiKiPay_Farsi_V6\app\src\main\res")
Write-Host "Watching: $ResPath (Ctrl+C to stop) ..."
$handler = {
  Start-Sleep -Milliseconds 300
  & powershell -NoProfile -ExecutionPolicy Bypass -File "D:\R&D_Project_2025\Project_ChiKiPay_2025\ChiKiPay_Farsi_V6\tools\Verify-ChiKiPayIcons.ps1" -ResPath $ResPath
}
$fsw = New-Object IO.FileSystemWatcher $ResPath, 'ic_*.webp'
$fsw.IncludeSubdirectories = $true
$fsw.EnableRaisingEvents   = $true
Register-ObjectEvent $fsw Changed -SourceIdentifier 'chi_changed' -Action $handler | Out-Null
Register-ObjectEvent $fsw Created -SourceIdentifier 'chi_created' -Action $handler | Out-Null
Register-ObjectEvent $fsw Deleted -SourceIdentifier 'chi_deleted' -Action $handler | Out-Null
Register-ObjectEvent $fsw Renamed -SourceIdentifier 'chi_renamed' -Action $handler | Out-Null
while($true){ Start-Sleep -Seconds 86400 }
