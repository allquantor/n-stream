package nmap

object Commands {
  final val BaseScan = "-T5 -p 1-1024 -sS -sV --webxml -oX - "
}
