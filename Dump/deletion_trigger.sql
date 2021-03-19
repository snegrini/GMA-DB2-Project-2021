CREATE DEFINER=`root`@`localhost` TRIGGER `questionnaire_BEFORE_DELETE` BEFORE DELETE ON `questionnaire` FOR EACH ROW BEGIN
	UPDATE `user` u
    SET u.Points = u.Points - (SELECT e1.Points FROM `entry` e1 WHERE e1.QuestionnaireId = OLD.Id AND e1.UserId = u.Id)
    WHERE u.Id IN (SELECT e2.UserId FROM `entry` e2 WHERE e2.QuestionnaireId = OLD.Id);
END