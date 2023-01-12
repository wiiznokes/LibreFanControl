<#
designed to work on Windows

Usage:
    -src :  path to the directory witch serve as reference for files names
    -rm :   path to the directory were files have to be removed
#>


if (!($args.Count -eq 0 -or $args.Count -eq 4))
{
    Write-Output "Error: need 0 or 4 arguments, see script for usage"
    exit 1
}

if ($args.Count -gt 0)
{
    # set source dir
    if ($args -contains "-src")
    {
        $srcDir = $args[($args.IndexOf("-src") + 1)]
    }
    else
    {
        Write-Output "Error: need -src argument"
        exit 1
    }

    # set remove dir
    if ($args -contains "-rm")
    {
        $rmDir = $args[($args.IndexOf("-rm") + 1)]
    }
    else
    {
        Write-Output "Error: need -rm argument"
        exit 1
    }
}
else {
    $srcDir = "./../include/windows/jvm"
    $rmDir  = "./../include/windows/app"
}

$srcFiles = Get-ChildItem $srcDir
$rmFiles = Get-ChildItem $rmDir

foreach ($src in $srcFiles)
{
    # Check if a file with the same name as the current source file exists in the remove directory
    $file = $rmFiles | Where-Object { $_.Name -eq $src.Name }
    if ($file)
    {
        Write-Output "Remove file $file"
        Remove-Item $file.FullName
    }
}