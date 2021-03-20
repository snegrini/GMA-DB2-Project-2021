CREATE DEFINER=`dev`@`localhost` TRIGGER `entry_BEFORE_DELETE` BEFORE DELETE ON `entry` FOR EACH ROW BEGIN
	UPDATE `user` u
    SET u.Points = u.Points - OLD.Points
    WHERE u.Id = OLD.UserId
		AND OLD.IsSubmitted = 1;
END