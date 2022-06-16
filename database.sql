CREATE DATABASE `efrei_fight`;

CREATE TABLE `efrei_fight`.`fight` (
  `fight_id` INT NOT NULL,
  `date` BIGINT NULL,
  `duration` INT NULL,
  `streetname` VARCHAR(80) NULL,
  `city` VARCHAR(80) NULL,
  PRIMARY KEY (`fight_id`));
  
ALTER TABLE `efrei_fight`.`fight` 
CHANGE COLUMN `fight_id` `fight_id` INT(11) NOT NULL AUTO_INCREMENT ;

CREATE TABLE `efrei_fight`.`fighter` (
  `fighter_id` INT NOT NULL,
  `fullname` VARCHAR(150) NOT NULL,
  `fight_id` INT NOT NULL,
  PRIMARY KEY (`fighter_id`),
  INDEX `FK_Fight_idx` (`fight_id` ASC),
  CONSTRAINT `FK_Fight`
    FOREIGN KEY (`fight_id`)
    REFERENCES `efrei_fight`.`fight` (`fight_id`)
    ON DELETE CASCADE
    ON UPDATE NO ACTION);

ALTER TABLE `efrei_fight`.`fighter` 
CHANGE COLUMN `fighter_id` `fighter_id` INT(11) NOT NULL AUTO_INCREMENT ;
    

CREATE TABLE `efrei_fight`.`round` (
  `round_id` INT NOT NULL,
  `duration` INT NULL,
  `fight_id` INT NOT NULL,
  PRIMARY KEY (`round_id`),
  INDEX `FK_Fight_Round_idx` (`fight_id` ASC),
  CONSTRAINT `FK_Fight_Round`
    FOREIGN KEY (`fight_id`)
    REFERENCES `efrei_fight`.`fight` (`fight_id`)
    ON DELETE CASCADE
    ON UPDATE NO ACTION);
    
ALTER TABLE `efrei_fight`.`round` 
CHANGE COLUMN `round_id` `round_id` INT(11) NOT NULL AUTO_INCREMENT ;

    
CREATE TABLE `efrei_fight`.`player_round_statistics` (
  `player_round_statistics_id` INT NOT NULL,
  `nbHook` INT NULL,
  `nbOvercute` INT NULL,
  `nbDirect` INT NULL,
  `nbKick` INT NULL,
  `fighter_id` INT NULL,
  PRIMARY KEY (`player_round_statistics_idfight`),
  INDEX `FK_Fighter_Stats_idx` (`fighter_id` ASC),
  CONSTRAINT `FK_Fighter_Stats`
    FOREIGN KEY (`fighter_id`)
    REFERENCES `efrei_fight`.`fighter` (`fighter_id`)
    ON DELETE CASCADE
    ON UPDATE NO ACTION);

ALTER TABLE `efrei_fight`.`player_round_statistics` 
ADD COLUMN `round` INT NOT NULL AFTER `fighter_id`;
    
ALTER TABLE `efrei_fight`.`player_round_statistics` 
CHANGE COLUMN `player_round_statistics_id` `player_round_statistics_id` INT(11) NOT NULL AUTO_INCREMENT ;
