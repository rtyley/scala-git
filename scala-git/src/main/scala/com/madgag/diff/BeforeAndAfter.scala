package com.madgag.diff

sealed trait BeforeAndAfter

case object Before extends BeforeAndAfter
case object After extends BeforeAndAfter