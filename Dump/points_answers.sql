CREATE DEFINER=`dev`@`localhost` TRIGGER `answer_AFTER_INSERT` AFTER INSERT ON `answer` FOR EACH ROW BEGIN
	UPDATE `user` u
    SET u.Points = u.Points + 1
    WHERE u.Id = (SELECT e.UserId FROM `entry` e WHERE e.Id = NEW.EntryId);
END