CREATE DEFINER=`dev`@`localhost` TRIGGER `answer_AFTER_INSERT` AFTER INSERT ON `answer` FOR EACH ROW BEGIN
    UPDATE `entry` e
    SET e.Points = e.Points + 1
    WHERE e.Id = NEW.EntryId;
END