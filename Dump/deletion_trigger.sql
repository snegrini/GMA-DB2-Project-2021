CREATE DEFINER=`dev`@`localhost` TRIGGER `entry_AFTER_DELETE` AFTER DELETE ON `entry` FOR EACH ROW BEGIN
	UPDATE `user` u
		SET u.Points = u.Points - OLD.Points
		WHERE u.Id = OLD.UserId
			AND OLD.IsSubmitted = 1;
END