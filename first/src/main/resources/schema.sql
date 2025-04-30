CREATE TABLE IF NOT EXISTS `employee` (
`id` INT AUTO_INCREMENT PRIMARY KEY,
`name` VARCHAR(30) NOT NULL,
`email` VARCHAR(30) NOT NULL,
`mobile_number` VARCHAR(12) NOT NULL,
`department` VARCHAR(30) NOT NULL,
`designation` VARCHAR(30) NOT NULL,
`created_at` DATE DEFAULT NULL,
`created_by` VARCHAR(20) DEFAULT NULL,
`updated_at` DATE DEFAULT NULL,
`updated_by` VARCHAR(20) DEFAULT NULL
);