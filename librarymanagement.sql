DROP DATABASE librarymanagement;
CREATE DATABASE librarymanagement;
USE librarymanagement;
SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Database: `librarymanagement`
--

-- --------------------------------------------------------

--
-- Table structure for table `admin`
--

CREATE TABLE `admin` (
  `admin_id` int(11) NOT NULL,
  `name` varchar(255) NOT NULL,
  `email` varchar(255) NOT NULL,
  `password` varchar(255) NOT NULL,
  `phone_number` varchar(20) DEFAULT NULL,
  `address` varchar(255) DEFAULT NULL,
  `created_at` timestamp NOT NULL DEFAULT current_timestamp()
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `admin`
--

INSERT INTO `admin` (`admin_id`, `name`, `email`, `password`, `phone_number`, `address`, `created_at`) VALUES
(1, 'admin', 'admin@example.com', '123', '0123456789', '123 abc', '2024-10-23 14:03:49');

-- --------------------------------------------------------

--
-- Table structure for table `books`
--

CREATE TABLE `books` (
  `id` int(11) NOT NULL,
  `title` varchar(255) NOT NULL,
  `author` varchar(255) DEFAULT NULL,
  `publisher` varchar(255) DEFAULT NULL,
  `publication_year` year(4) DEFAULT NULL,
  `isbn` varchar(20) DEFAULT NULL,
  `page_count` int(11) DEFAULT NULL,
  `quantity` int(11) DEFAULT 0,
  `category_id` int(11) DEFAULT NULL,
  `google_id` varchar(255) DEFAULT NULL,
  `description` text DEFAULT NULL,
  `thumbnail` varchar(255) DEFAULT NULL,
  `language` varchar(20) DEFAULT NULL,
  `average_rating` float DEFAULT NULL,
  `ratings_count` int(11) DEFAULT NULL,
  `preview_link` varchar(255) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `books`
--

INSERT INTO `books` (`id`, `title`, `author`, `publisher`, `publication_year`, `isbn`, `page_count`, `quantity`, `category_id`, `google_id`, `description`, `thumbnail`, `language`, `average_rating`, `ratings_count`, `preview_link`) VALUES
(120, 'Harry Potter and the Philosopher\'s Stone', 'J.K. Rowling', 'Bloomsbury', '1997', '978-0-7475-3269-9', 309, 15, NULL, NULL, 'A young wizard begins his magical journey', 'http://example.com/thumbnail1.jpg', 'en', 4.9, 100000, 'https://cdn0.fahasa.com/media/catalog/product/9/7/9781408855898.jpeg'),
(122, 'To Kill a Mockingbird', 'Harper Lee', 'J.B. Lippincott & Co.', '1960', '978-0-06-112008-4', 281, 30, NULL, NULL, 'A story of racial injustice in the American South', 'http://example.com/thumbnail3.jpg', 'en', 4.8, 120000, 'https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcTvCC1ng4Ed5xDF7jBcI9Qin8FqXo4D-LPbtQ&s'),
(123, 'The Great Gatsby', 'F. Scott Fitzgerald', 'Charles Scribner\'s Sons', '1925', '978-0-7432-7356-5', 180, 25, NULL, NULL, 'A tale of wealth and tragedy in the Jazz Age', 'http://example.com/thumbnail4.jpg', 'en', 4.2, 150000, 'https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcQ6KCs9CjBU5tlMQtZAO4n5PZznmeSafQ_tgA&s'),
(124, 'One Hundred Years of Solitude', 'Gabriel Garcia Marquez', 'Editorial Sudamericana', '1967', '978-0-06-088328-7', 417, 12, NULL, NULL, 'A magical realism novel spanning a family\'s history', 'http://example.com/thumbnail5.jpg', 'spa', 4.5, 85000, 'https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcSP7K80HlXo71huqEusKwVgARIpATv1iFFv0A&s'),
(125, 'The Catcher in the Rye', 'J.D. Salinger', 'Little, Brown and Company', '1951', '978-0-316-76948-0', 214, 20, NULL, NULL, 'The story of a young man lost in the modern world', 'http://example.com/thumbnail7.jpg', 'en', 4.1, 95000, 'https://images-na.ssl-images-amazon.com/images/S/compressed.photo.goodreads.com/books/1398034300i/5107.jpg'),
(126, 'A Game of Thrones', 'George R.R. Martin', 'Bantam Books', '1996', '978-0-553-89784-5', 835, 35, NULL, NULL, 'Epic fantasy series about a war for the throne', 'http://example.com/thumbnail15.jpg', 'en', 4.7, 230000, 'https://m.media-amazon.com/images/I/71Jzezm8CBL._AC_UF894,1000_QL80_.jpg'),
(127, 'Crime and Punishment', 'Fyodor Dostoevsky', 'The Russian Messenger', '1966', '978-0-14-044913-5', 671, 10, NULL, NULL, 'A man’s internal struggle with morality', 'http://example.com/thumbnail16.jpg', 'ru', 4.6, 94000, 'https://m.media-amazon.com/images/I/612KmKeEYEL._AC_UF1000,1000_QL80_.jpg'),
(128, 'Brave New World', 'Aldous Huxley', 'Chatto & Windus', '1932', '978-0-06-085052-4', 288, 15, NULL, NULL, 'A future society controlled by technology and drugs', 'http://example.com/thumbnail17.jpg', 'en', 4.5, 112000, 'https://upload.wikimedia.org/wikipedia/en/6/62/BraveNewWorld_FirstEdition.jpg'),
(129, 'Lord of the Flies', 'William Golding', 'Faber and Faber', '1954', '978-0-399-50148-7', 224, 15, NULL, NULL, 'Boys stranded on an island resort to savagery', 'http://example.com/thumbnail18.jpg', 'en', 4, 103000, 'https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcS9kK-W5fU3dRkfsiyUbe_6nDa4f27cCLDT1w&s'),
(130, 'The Picture of Dorian Gray', 'Oscar Wilde', 'Lippincott\'s Monthly Magazine', '1990', '978-0-14-143957-0', 254, 10, NULL, NULL, 'A man\'s portrait that ages while he stays young', 'http://example.com/thumbnail19.jpg', 'en', 4.1, 89000, 'https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcQ2f1yBAWXHpHATryDEaVozatMu51pQFMGiDg&s'),
(131, 'The Hitchhiker\'s Guide to the Galaxy', 'Douglas Adams', 'Pan Books', '1979', '978-0-345-39180-3', 193, 25, NULL, NULL, 'A comedic journey through space', 'http://example.com/thumbnail20.jpg', 'en', 4.4, 97000, 'https://product.hstatic.net/200000168971/product/9781529046137_54cc262f09274687ad0c1e907f9a5dd7_master.jpg'),
(132, 'Fahrenheit 451', 'Ray Bradbury', 'Ballantine Books', '1953', '978-0-345-39180-3', 249, 25, NULL, NULL, 'A dystopian novel about a future where books are banned', 'http://example.com/thumbnail21.jpg', 'en', 4.3, 85000, 'https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcQtk9U2xvhPbV4XrmsZ1RRaijEdbci7Y6R0xA&s'),
(133, 'Love in the Time of Cholera', 'Gabriel Garcia Marquez', 'Harper & Row', '1985', '978-0-06-088328-7', 348, 15, NULL, NULL, 'A story of love and solitude in a tropical town', 'http://example.com/thumbnail22.jpg', 'spa', 4.6, 100000, 'https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcQfiQJhoVGpkah1V2DiA8KYPuItLzjP5blxAw&s'),
(134, 'The Bluest Eye', 'Toni Morrison', 'Knopf', '1970', '978-0-452-28423-4', 224, 20, NULL, NULL, 'A novel exploring the life of an African American girl', 'http://example.com/thumbnail23.jpg', 'en', 4.5, 78000, 'https://m.media-amazon.com/images/I/71RQurEFKSL._AC_UF894,1000_QL80_.jpg'),
(135, 'The Shining', 'Stephen King', 'Doubleday', '1977', '978-0-385-12167-5', 447, 30, NULL, NULL, 'A horror novel about a haunted hotel', 'http://example.com/thumbnail24.jpg', 'en', 4.4, 150000, 'https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcSCYVMBXDOXKabnDY38Q2nl2ABd6PCn8CR6Kg&s'),
(136, 'Kafka on the Shore', 'Haruki Murakami', 'Harvill Secker', '2002', '978-1-56947-731-0', 307, 12, NULL, NULL, 'A surreal tale of love and loss', 'http://example.com/thumbnail25.jpg', 'ja', 4.3, 60000, 'https://images-na.ssl-images-amazon.com/images/S/compressed.photo.goodreads.com/books/1429638085i/4929.jpg'),
(137, 'The Handmaid\'s Tale', 'Margaret Atwood', 'McClelland & Stewart', '1985', '978-0-385-40828-2', 311, 15, NULL, NULL, 'A speculative fiction about a dystopian future', 'http://example.com/thumbnail26.jpg', 'en', 4.5, 82000, 'https://m.media-amazon.com/images/I/61su39k8NUL._AC_UF1000,1000_QL80_.jpg'),
(138, 'American Gods', 'Neil Gaiman', 'William Morrow', '2001', '978-0-380-97883-8', 624, 20, NULL, NULL, 'A fantasy tale blending mythology and reality', 'http://example.com/thumbnail27.jpg', 'en', 4.6, 94000, 'https://m.media-amazon.com/images/I/716LpMKQ3iL._AC_UF1000,1000_QL80_.jpg'),
(139, 'The Grapes of Wrath', 'John Steinbeck', 'Viking Press', '1939', '978-0-14-303943-3', 464, 15, NULL, NULL, 'A novel about the Great Depression and migrant workers', 'http://example.com/thumbnail28.jpg', 'en', 4.4, 75000, 'https://m.media-amazon.com/images/I/81yN7h6yG-L._AC_UF1000,1000_QL80_.jpg'),
(140, 'Do Androids Dream of Electric Sheep?', 'Philip K. Dick', 'Doubleday', '1968', '978-0-345-39180-3', 264, 10, NULL, NULL, 'A science fiction story questioning reality', 'http://example.com/thumbnail29.jpg', 'en', 4.2, 67000, 'https://upload.wikimedia.org/wikipedia/commons/e/ee/DoAndroidsDream.png'),
(141, 'Half of a Yellow Sun', 'Chimamanda Ngozi Adichie', 'Knopf', '2006', '978-0-307-38936-5', 464, 15, NULL, NULL, 'A tale of identity and cultural conflict', 'http://example.com/thumbnail30.jpg', 'en', 4.5, 89000, 'https://bizweb.dktcdn.net/thumb/1024x1024/100/364/248/products/81qrgwbdczl-sl1168.jpg?v=1701258904523'),
(143, 'Dracula', 'Bram Stoker', 'Archibald Constable and Company', '1990', NULL, NULL, 0, NULL, NULL, NULL, NULL, 'en', NULL, NULL, 'https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcQmjOpDsOc96Kku52fnw6hXlPMt3MZam3276A&s'),
(144, 'Frankenstein', 'Mary Shelley', 'Lackington, Hughes, Harding, Mavor & Jones', '1901', NULL, NULL, 0, NULL, NULL, NULL, NULL, 'en', NULL, NULL, 'https://m.media-amazon.com/images/I/81z7E0uWdtL.jpg'),
(145, 'The Haunting of Hill House', 'Shirley Jackson', 'Viking Press', '1959', NULL, NULL, 0, NULL, NULL, NULL, NULL, 'en', NULL, NULL, 'https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcTH2Snqp1lFFpii7wFCqaugK2vaR8_cN3yR0w&s'),
(146, 'It', 'Stephen King', 'Viking Press', '1986', NULL, NULL, 0, NULL, NULL, NULL, NULL, 'en', NULL, NULL, 'https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcTOWeovjakBhrcmB7WNbNmM6MHXrdSUGeFl1A&s'),
(147, 'Meditations', 'Marcus Aurelius', 'Self-Published', '1901', NULL, NULL, 0, NULL, NULL, NULL, NULL, 'en', NULL, NULL, 'https://m.media-amazon.com/images/I/71FCbiv0tTL._AC_UF1000,1000_QL80_.jpg'),
(148, 'Thus Spoke Zarathustra', 'Friedrich Nietzsche', 'Eviternity', '1993', NULL, NULL, 0, NULL, NULL, NULL, NULL, 'en', NULL, NULL, 'https://images-na.ssl-images-amazon.com/images/I/613ZVoVVeXL.jpg'),
(149, 'The Republic', 'Plato', 'Various Publishers', '1901', NULL, NULL, 0, NULL, NULL, NULL, NULL, 'en', NULL, NULL, 'https://m.media-amazon.com/images/I/91MRDNc-mIL._UF1000,1000_QL80_.jpg'),
(150, 'Being and Time', 'Martin Heidegger', 'Niemeyer', '1927', NULL, NULL, 0, NULL, NULL, NULL, NULL, 'en', NULL, NULL, 'https://m.media-amazon.com/images/I/6150bIphneL._AC_UF1000,1000_QL80_.jpg'),
(151, 'The Second Sex', 'Simone de Beauvoir', 'Gallimard', '1949', NULL, NULL, 0, NULL, NULL, NULL, NULL, 'en', NULL, NULL, 'https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcQ-VSBMHlJbBofeZUsDsJsZMm17coDN9HAGVw&s'),
(153, 'Pride and Prejudice', 'No author found', 'No Publisher', '2019', NULL, NULL, 0, NULL, NULL, NULL, NULL, 'vi', NULL, NULL, 'https://m.media-amazon.com/images/I/712P0p5cXIL._AC_UF1000,1000_QL80_.jpg'),
(160, 'Sherlock Holmes - 101 Amazingly True Facts You Didn\'t Know', 'No author detected', '101BookFacts.com (pub-5999650418488591)', '2014', NULL, NULL, 0, NULL, NULL, NULL, NULL, 'en', NULL, NULL, 'https://rukminim2.flixcart.com/image/850/1000/kk76wsw0/book/z/8/x/the-memoirs-of-sherlock-holmes-original-imafzhvzahzww2tx.jpeg?q=90&crop=false');

--
-- Triggers `books`
--
DELIMITER $$
CREATE TRIGGER `before_insert_books` BEFORE INSERT ON `books` FOR EACH ROW BEGIN
    -- Thêm bản ghi mới vào bảng Documents
    INSERT INTO Documents (title, author, quantity, description, language, publication_year) 
    VALUES (NEW.title, NEW.author, NEW.quantity, NEW.description, NEW.language, NEW.publication_year);
    -- Lấy ID của bản ghi vừa tạo trong bảng Documents
    SET NEW.id = LAST_INSERT_ID(); -- Đặt id của Theses bằng id vừa tạo của Documents
END
$$
DELIMITER ;

-- --------------------------------------------------------

--
-- Table structure for table `borrow_records`
--

CREATE TABLE `borrow_records` (
  `record_id` int(11) NOT NULL,
  `document_id` int(11) DEFAULT NULL,
  `member_id` int(11) DEFAULT NULL,
  `borrow_date` date NOT NULL,
  `return_date` date DEFAULT NULL,
  `due_date` date NOT NULL,
  `status` VARCHAR(17) DEFAULT 'borrowed',
  `quantity` int(11) DEFAULT NULL,
  `quantity_borrow` int(11) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- --------------------------------------------------------

--
-- Table structure for table `categories`
--

CREATE TABLE `categories` (
  `category_id` int(11) NOT NULL,
  `name` varchar(255) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `categories`
--

INSERT INTO `categories` (`category_id`, `name`) VALUES
(1, 'Fantasy'),
(2, 'Science Fiction'),
(3, 'Mystery'),
(4, 'Literature'),
(5, 'Historical Fiction');

-- --------------------------------------------------------

--
-- Table structure for table `documents`
--

CREATE TABLE `documents` (
  `id` int(11) NOT NULL,
  `title` varchar(255) NOT NULL,
  `author` varchar(255) DEFAULT NULL,
  `quantity` int(11) DEFAULT 0,
  `description` text DEFAULT NULL,
  `language` varchar(20) DEFAULT NULL,
  `publication_year` year(4) DEFAULT NULL,
  `google_id` varchar(255) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `documents`
--

INSERT INTO `documents` (`id`, `title`, `author`, `quantity`, `description`, `language`, `publication_year`, `google_id`) VALUES
(8, 'Harry Potter and the Philosopher\'s Stone', 'J.K. Rowling', 30, 'Fantasy novel about a young wizard', 'en', '1997', NULL),
(9, '1984', 'George Orwell', 20, 'Dystopian novel set in a totalitarian regime', 'en', '1949', NULL),
(10, 'Pride and Prejudice', 'Jane Austen', 15, 'A classic romantic novel', 'en', '0000', NULL),
(11, 'Adventures of Huckleberry Finn', 'Mark Twain', 25, 'Story of a young boy\'s adventures', 'en', '0000', NULL),
(12, 'The Lord of the Rings', 'J.R.R. Tolkien', 40, 'Epic fantasy journey', 'en', '1954', NULL),
(13, 'One Hundred Years of Solitude', 'Gabriel Garcia Marquez', 10, 'Magical realism novel about a family', 'es', '1967', NULL),
(14, 'War and Peace', 'Leo Tolstoy', 15, 'Historical novel set in Russia', 'ru', '0000', NULL),
(15, 'Crime and Punishment', 'Fyodor Dostoevsky', 10, 'Psychological drama novel', 'ru', '0000', NULL),
(16, 'The Odyssey', 'Homer', 5, 'Ancient Greek epic poem', 'gr', '0000', NULL),
(17, 'Moby-Dick', 'Herman Melville', 12, 'Story of a man\'s obsession with a whale', 'en', '0000', NULL),
(18, 'The Great Gatsby', 'F. Scott Fitzgerald', 20, 'Novel set in the Jazz Age', 'en', '1925', NULL),
(19, 'Don Quixote', 'Miguel de Cervantes', 5, 'Story of a man on a quest as a knight', 'es', '0000', NULL),
(20, 'A Tale of Two Cities', 'Charles Dickens', 18, 'Novel set during the French Revolution', 'en', '0000', NULL),
(21, 'Les Misérables', 'Victor Hugo', 7, 'Epic novel set in post-revolutionary France', 'fr', '0000', NULL),
(22, 'To Kill a Mockingbird', 'Harper Lee', 20, 'Novel about racial injustice in the Deep South', 'en', '1960', NULL),
(23, 'Frankenstein', 'Mary Shelley', 14, 'Gothic novel about a scientist\'s experiment', 'en', '0000', NULL),
(24, 'The Catcher in the Rye', 'J.D. Salinger', 18, 'Novel about a young man\'s experiences in NYC', 'en', '1951', NULL),
(25, 'Wuthering Heights', 'Emily Brontë', 10, 'Gothic novel set on the Yorkshire moors', 'en', '0000', NULL),
(26, 'Brave New World', 'Aldous Huxley', 12, 'Dystopian novel about a future society', 'en', '1932', NULL),
(27, 'Heart of Darkness', 'Joseph Conrad', 6, 'Novel about a journey up the Congo River', 'en', '0000', NULL),
(28, 'The Hitchhiker\'s Guide to the Galaxy', 'Douglas Adams', 25, 'Sci-fi comedy novel about space adventure', 'en', '1979', NULL),
(29, 'Slaughterhouse-Five', 'Kurt Vonnegut', 10, 'Dark comedy about WWII experiences', 'en', '1969', NULL),
(30, 'The Picture of Dorian Gray', 'Oscar Wilde', 8, 'Novel about a man who never ages', 'en', '0000', NULL),
(31, 'Lord of the Flies', 'William Golding', 15, 'Novel about boys stranded on an island', 'en', '1954', NULL),
(32, 'Harry Potter and the Philosopher\'s Stone', 'J.K. Rowling', 30, 'Fantasy novel about a young wizard', 'en', '1997', NULL),
(33, '1984', 'George Orwell', 20, 'Dystopian novel set in a totalitarian regime', 'en', '1949', NULL),
(34, 'Pride and Prejudice', 'Jane Austen', 15, 'A classic romantic novel', 'en', '0000', NULL),
(35, 'Adventures of Huckleberry Finn', 'Mark Twain', 25, 'Story of a young boy\'s adventures', 'en', '0000', NULL),
(36, 'The Lord of the Rings', 'J.R.R. Tolkien', 40, 'Epic fantasy journey', 'en', '1954', NULL),
(37, 'One Hundred Years of Solitude', 'Gabriel Garcia Marquez', 10, 'Magical realism novel about a family', 'es', '1967', NULL),
(38, 'War and Peace', 'Leo Tolstoy', 15, 'Historical novel set in Russia', 'ru', '0000', NULL),
(39, 'Crime and Punishment', 'Fyodor Dostoevsky', 10, 'Psychological drama novel', 'ru', '0000', NULL),
(40, 'The Odyssey', 'Homer', 5, 'Ancient Greek epic poem', 'gr', '0000', NULL),
(41, 'Moby-Dick', 'Herman Melville', 12, 'Story of a man\'s obsession with a whale', 'en', '0000', NULL),
(42, 'The Great Gatsby', 'F. Scott Fitzgerald', 20, 'Novel set in the Jazz Age', 'en', '1925', NULL),
(43, 'Don Quixote', 'Miguel de Cervantes', 5, 'Story of a man on a quest as a knight', 'es', '0000', NULL),
(44, 'A Tale of Two Cities', 'Charles Dickens', 18, 'Novel set during the French Revolution', 'en', '0000', NULL),
(45, 'Les Misérables', 'Victor Hugo', 7, 'Epic novel set in post-revolutionary France', 'fr', '0000', NULL),
(46, 'To Kill a Mockingbird', 'Harper Lee', 20, 'Novel about racial injustice in the Deep South', 'en', '1960', NULL),
(47, 'Frankenstein', 'Mary Shelley', 14, 'Gothic novel about a scientist\'s experiment', 'en', '0000', NULL),
(48, 'The Catcher in the Rye', 'J.D. Salinger', 18, 'Novel about a young man\'s experiences in NYC', 'en', '1951', NULL),
(49, 'Wuthering Heights', 'Emily Brontë', 10, 'Gothic novel set on the Yorkshire moors', 'en', '0000', NULL),
(50, 'Brave New World', 'Aldous Huxley', 12, 'Dystopian novel about a future society', 'en', '1932', NULL),
(51, 'Heart of Darkness', 'Joseph Conrad', 6, 'Novel about a journey up the Congo River', 'en', '0000', NULL),
(52, 'The Hitchhiker\'s Guide to the Galaxy', 'Douglas Adams', 25, 'Sci-fi comedy novel about space adventure', 'en', '1979', NULL),
(53, 'Slaughterhouse-Five', 'Kurt Vonnegut', 10, 'Dark comedy about WWII experiences', 'en', '1969', NULL),
(54, 'The Picture of Dorian Gray', 'Oscar Wilde', 8, 'Novel about a man who never ages', 'en', '0000', NULL),
(55, 'Lord of the Flies', 'William Golding', 15, 'Novel about boys stranded on an island', 'en', '1954', NULL),
(68, 'Harry Potter and the Philosopher\'s Stone', 'J.K. Rowling', 15, 'A young wizard begins his magical journey', 'en', '1997', NULL),
(69, '1984', 'George Orwell', 20, 'A dystopian society under total surveillance', 'en', '1949', NULL),
(70, 'To Kill a Mockingbird', 'Harper Lee', 30, 'A story of racial injustice in the American South', 'en', '1960', NULL),
(71, 'The Great Gatsby', 'F. Scott Fitzgerald', 25, 'A tale of wealth and tragedy in the Jazz Age', 'en', '1925', NULL),
(72, 'One Hundred Years of Solitude', 'Gabriel Garcia Marquez', 12, 'A magical realism novel spanning a family\'s history', 'es', '1967', NULL),
(73, 'Frankenstein', 'Mary Shelley', 10, 'A scientist creates a creature from assembled body parts', 'en', '0000', NULL),
(74, 'The Catcher in the Rye', 'J.D. Salinger', 20, 'The story of a young man lost in the modern world', 'en', '1951', NULL),
(75, 'The Odyssey', 'Homer', 5, 'Epic journey of a man returning home from war', 'gr', '0000', NULL),
(76, 'Pride and Prejudice', 'Jane Austen', 15, 'A story of love and social standings', 'en', '0000', NULL),
(77, 'Adventures of Huckleberry Finn', 'Mark Twain', 25, 'A young boy’s adventure on the Mississippi River', 'en', '0000', NULL),
(78, 'A Tale of Two Cities', 'Charles Dickens', 18, 'Story set during the French Revolution', 'en', '0000', NULL),
(79, 'Don Quixote', 'Miguel de Cervantes', 10, 'A knight\'s adventures in Spain', 'es', '0000', NULL),
(80, 'Moby-Dick', 'Herman Melville', 12, 'A man’s obsession with a whale', 'en', '0000', NULL),
(81, 'Les Misérables', 'Victor Hugo', 7, 'Struggles of people in post-revolutionary France', 'fr', '0000', NULL),
(82, 'A Game of Thrones', 'George R.R. Martin', 35, 'Epic fantasy series about a war for the throne', 'en', '1996', NULL),
(83, 'Crime and Punishment', 'Fyodor Dostoevsky', 10, 'A man’s internal struggle with morality', 'ru', '0000', NULL),
(84, 'Brave New World', 'Aldous Huxley', 15, 'A future society controlled by technology and drugs', 'en', '1932', NULL),
(85, 'Lord of the Flies', 'William Golding', 15, 'Boys stranded on an island resort to savagery', 'en', '1954', NULL),
(86, 'The Picture of Dorian Gray', 'Oscar Wilde', 10, 'A man\'s portrait that ages while he stays young', 'en', '0000', NULL),
(87, 'The Hitchhiker\'s Guide to the Galaxy', 'Douglas Adams', 25, 'A comedic journey through space', 'en', '1979', NULL),
(88, 'Harry Potter and the Philosopher\'s Stone', 'J.K. Rowling', 15, 'A young wizard begins his magical journey', 'en', '1997', NULL),
(89, '1984', 'George Orwell', 20, 'A dystopian society under total surveillance', 'en', '1949', NULL),
(90, 'To Kill a Mockingbird', 'Harper Lee', 30, 'A story of racial injustice in the American South', 'en', '1960', NULL),
(91, 'The Great Gatsby', 'F. Scott Fitzgerald', 25, 'A tale of wealth and tragedy in the Jazz Age', 'en', '1925', NULL),
(92, 'One Hundred Years of Solitude', 'Gabriel Garcia Marquez', 12, 'A magical realism novel spanning a family\'s history', 'es', '1967', NULL),
(93, 'Frankenstein', 'Mary Shelley', 10, 'A scientist creates a creature from assembled body parts', 'en', '0000', NULL),
(94, 'The Catcher in the Rye', 'J.D. Salinger', 20, 'The story of a young man lost in the modern world', 'en', '1951', NULL),
(95, 'The Odyssey', 'Homer', 5, 'Epic journey of a man returning home from war', 'gr', '0000', NULL),
(96, 'Pride and Prejudice', 'Jane Austen', 15, 'A story of love and social standings', 'en', '0000', NULL),
(97, 'Adventures of Huckleberry Finn', 'Mark Twain', 25, 'A young boy’s adventure on the Mississippi River', 'en', '0000', NULL),
(98, 'A Tale of Two Cities', 'Charles Dickens', 18, 'Story set during the French Revolution', 'en', '0000', NULL),
(99, 'Don Quixote', 'Miguel de Cervantes', 10, 'A knight\'s adventures in Spain', 'es', '0000', NULL),
(100, 'Moby-Dick', 'Herman Melville', 12, 'A man’s obsession with a whale', 'en', '0000', NULL),
(101, 'Les Misérables', 'Victor Hugo', 7, 'Struggles of people in post-revolutionary France', 'fr', '0000', NULL),
(102, 'A Game of Thrones', 'George R.R. Martin', 35, 'Epic fantasy series about a war for the throne', 'en', '1996', NULL),
(103, 'Crime and Punishment', 'Fyodor Dostoevsky', 10, 'A man’s internal struggle with morality', 'ru', '0000', NULL),
(104, 'Brave New World', 'Aldous Huxley', 15, 'A future society controlled by technology and drugs', 'en', '1932', NULL),
(105, 'Lord of the Flies', 'William Golding', 15, 'Boys stranded on an island resort to savagery', 'en', '1954', NULL),
(106, 'The Picture of Dorian Gray', 'Oscar Wilde', 10, 'A man\'s portrait that ages while he stays young', 'en', '0000', NULL),
(107, 'The Hitchhiker\'s Guide to the Galaxy', 'Douglas Adams', 25, 'A comedic journey through space', 'en', '1979', NULL),
(108, 'Harry Potter and the Philosopher\'s Stone', 'J.K. Rowling', 15, 'A young wizard begins his magical journey', 'en', '1997', NULL),
(109, '1984', 'George Orwell', 20, 'A dystopian society under total surveillance', 'en', '1949', NULL),
(110, 'To Kill a Mockingbird', 'Harper Lee', 30, 'A story of racial injustice in the American South', 'en', '1960', NULL),
(111, 'The Great Gatsby', 'F. Scott Fitzgerald', 25, 'A tale of wealth and tragedy in the Jazz Age', 'en', '1925', NULL),
(112, 'One Hundred Years of Solitude', 'Gabriel Garcia Marquez', 12, 'A magical realism novel spanning a family\'s history', 'es', '1967', NULL),
(113, 'The Catcher in the Rye', 'J.D. Salinger', 20, 'The story of a young man lost in the modern world', 'en', '1951', NULL),
(114, 'A Game of Thrones', 'George R.R. Martin', 35, 'Epic fantasy series about a war for the throne', 'en', '1996', NULL),
(115, 'Crime and Punishment', 'Fyodor Dostoevsky', 10, 'A man’s internal struggle with morality', 'ru', '0000', NULL),
(116, 'Brave New World', 'Aldous Huxley', 15, 'A future society controlled by technology and drugs', 'en', '1932', NULL),
(117, 'Lord of the Flies', 'William Golding', 15, 'Boys stranded on an island resort to savagery', 'en', '1954', NULL),
(118, 'The Picture of Dorian Gray', 'Oscar Wilde', 10, 'A man\'s portrait that ages while he stays young', 'en', '0000', NULL),
(119, 'The Hitchhiker\'s Guide to the Galaxy', 'Douglas Adams', 25, 'A comedic journey through space', 'en', '1979', NULL),
(120, 'Harry Potter and the Philosopher\'s Stone', 'J.K. Rowling', 15, 'A young wizard begins his magical journey', 'en', '1997', NULL),
(121, '1984', 'George Orwell', 20, 'A dystopian society under total surveillance', 'en', '1949', NULL),
(122, 'To Kill a Mockingbird', 'Harper Lee', 30, 'A story of racial injustice in the American South', 'en', '1960', NULL),
(123, 'The Great Gatsby', 'F. Scott Fitzgerald', 25, 'A tale of wealth and tragedy in the Jazz Age', 'en', '1925', NULL),
(124, 'One Hundred Years of Solitude', 'Gabriel Garcia Marquez', 12, 'A magical realism novel spanning a family\'s history', 'es', '1967', NULL),
(125, 'The Catcher in the Rye', 'J.D. Salinger', 20, 'The story of a young man lost in the modern world', 'en', '1951', NULL),
(126, 'A Game of Thrones', 'George R.R. Martin', 35, 'Epic fantasy series about a war for the throne', 'en', '1996', NULL),
(127, 'Crime and Punishment', 'Fyodor Dostoevsky', 10, 'A man’s internal struggle with morality', 'ru', '1966', NULL),
(128, 'Brave New World', 'Aldous Huxley', 15, 'A future society controlled by technology and drugs', 'en', '1932', NULL),
(129, 'Lord of the Flies', 'William Golding', 15, 'Boys stranded on an island resort to savagery', 'en', '1954', NULL),
(130, 'The Picture of Dorian Gray', 'Oscar Wilde', 10, 'A man\'s portrait that ages while he stays young', 'en', '1990', NULL),
(131, 'The Hitchhiker\'s Guide to the Galaxy', 'Douglas Adams', 25, 'A comedic journey through space', 'en', '1979', NULL),
(132, 'Fahrenheit 451', 'Ray Bradbury', 25, 'A dystopian novel about a future where books are banned', 'en', '1953', NULL),
(133, 'Love in the Time of Cholera', 'Gabriel Garcia Marquez', 15, 'A story of love and solitude in a tropical town', 'es', '1985', NULL),
(134, 'The Bluest Eye', 'Toni Morrison', 20, 'A novel exploring the life of an African American girl', 'en', '1970', NULL),
(135, 'The Shining', 'Stephen King', 30, 'A horror novel about a haunted hotel', 'en', '1977', NULL),
(136, 'Kafka on the Shore', 'Haruki Murakami', 12, 'A surreal tale of love and loss', 'ja', '2002', NULL),
(137, 'The Handmaid\'s Tale', 'Margaret Atwood', 15, 'A speculative fiction about a dystopian future', 'en', '1985', NULL),
(138, 'American Gods', 'Neil Gaiman', 20, 'A fantasy tale blending mythology and reality', 'en', '2001', NULL),
(139, 'The Grapes of Wrath', 'John Steinbeck', 15, 'A novel about the Great Depression and migrant workers', 'en', '1939', NULL),
(140, 'Do Androids Dream of Electric Sheep?', 'Philip K. Dick', 10, 'A science fiction story questioning reality', 'en', '1968', NULL),
(141, 'Half of a Yellow Sun', 'Chimamanda Ngozi Adichie', 15, 'A tale of identity and cultural conflict', 'en', '2006', NULL),
(142, '', NULL, 0, NULL, NULL, NULL, NULL),
(143, 'Dracula', 'Bram Stoker', 0, NULL, 'English', '0000', NULL),
(144, 'Frankenstein', 'Mary Shelley', 0, NULL, 'English', '0000', NULL),
(145, 'The Haunting of Hill House', 'Shirley Jackson', 0, NULL, 'English', '1959', NULL),
(146, 'It', 'Stephen King', 0, NULL, 'English', '1986', NULL),
(147, 'Meditations', 'Marcus Aurelius', 0, NULL, 'English', '1901', NULL),
(148, 'Thus Spoke Zarathustra', 'Friedrich Nietzsche', 0, NULL, 'English', '1993', NULL),
(149, 'The Republic', 'Plato', 0, NULL, 'English', '1901', NULL),
(150, 'Being and Time', 'Martin Heidegger', 0, NULL, 'English', '1927', NULL),
(151, 'The Second Sex', 'Simone de Beauvoir', 0, NULL, 'English', '1949', NULL),
(152, 'Participatory Research on Child Maltreatment with Children and Adult Survivors', 'No author detected', 0, NULL, 'en', '2023', NULL),
(153, 'Pride and Prejudice', 'No author found', 0, NULL, 'vi', '2019', NULL),
(154, 'Chờ đợi chẳng dễ chút nào!', 'No author detected', 0, NULL, 'vi', '2018', NULL),
(155, 'Chờ đợi chẳng dễ chút nào!', 'No author detected', 0, NULL, 'vi', '2018', NULL),
(156, 'Chờ đợi chẳng dễ chút nào!', 'No author detected', 0, NULL, 'vi', '2018', NULL),
(157, 'Chờ đợi chẳng dễ chút nào!', 'No author detected', 0, NULL, 'vi', '2018', NULL),
(158, 'Chờ đợi chẳng dễ chút nào!', 'No author detected', 0, NULL, 'vi', '2018', NULL),
(159, 'The Cuckoo\'s Calling', 'No author detected', 0, NULL, 'en', '2014', NULL),
(160, 'Sherlock Holmes - 101 Amazingly True Facts You Didn\'t Know', 'No author detected', 0, NULL, 'en', '2014', NULL);

-- --------------------------------------------------------

--
-- Table structure for table `governmentdocuments`
--

CREATE TABLE `governmentdocuments` (
  `id` int(11) NOT NULL,
  `title` varchar(255) NOT NULL,
  `author` varchar(255) DEFAULT NULL,
  `document_type` varchar(255) DEFAULT NULL,
  `publication_year` year(4) DEFAULT NULL,
  `quantity` int(11) DEFAULT 0,
  `description` text DEFAULT NULL,
  `language` varchar(20) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Triggers `governmentdocuments`
--
DELIMITER $$
CREATE TRIGGER `before_insert_governmentdocuments` BEFORE INSERT ON `governmentdocuments` FOR EACH ROW BEGIN
    -- Thêm bản ghi mới vào bảng Documents
    INSERT INTO Documents (title, author, quantity, description, language, publication_year) 
    VALUES (NEW.title, NEW.author, NEW.quantity, NEW.description, NEW.language, NEW.publication_year);
    -- Lấy ID của bản ghi vừa tạo trong bảng Documents
    SET NEW.id = LAST_INSERT_ID(); -- Đặt id của GovernmentDocuments bằng id vừa tạo của Documents
END
$$
DELIMITER ;

-- --------------------------------------------------------

--
-- Table structure for table `members`
--

CREATE TABLE `members` (
  `member_id` int(11) NOT NULL,
  `name` varchar(255) NOT NULL,
  `address` varchar(255) DEFAULT NULL,
  `phone_number` varchar(20) DEFAULT NULL,
  `email` varchar(255) DEFAULT NULL,
  `membership_date` timestamp NOT NULL DEFAULT current_timestamp(),
  `password` varchar(255) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `members`
--

INSERT INTO `members` (`member_id`, `name`, `address`, `phone_number`, `email`, `membership_date`, `password`) VALUES
(2, 'Bob Johnson', '789 Reader Rd, Booktown', '0123487654', 'bob@example.com', '2023-03-19 17:00:00', 'bobpassword'),
(3, 'Charlie Brown', '321 Novel Ave, Booktown', '0978654321', 'charlie@example.com', '2023-07-21 17:00:00', 'charliepassword'),
(4, 'Daisy Evans', '654 Fiction Blvd, Booktown', '0123345678', 'daisy@example.com', '2023-09-11 17:00:00', 'daisypassword'),
(5, 'huy', 'BN', '01234567', 'huy@', '2024-11-05 02:43:33', '123');

-- --------------------------------------------------------

--
-- Table structure for table `reviews`
--

CREATE TABLE `reviews` (
  `review_id` int(11) NOT NULL,
  `document_id` int(11) DEFAULT NULL,
  `member_id` int(11) DEFAULT NULL,
  `rating` int(11) DEFAULT NULL CHECK (`rating` between 1 and 5),
  `comment` text DEFAULT NULL,
  `review_date` date NOT NULL,
  `book_id` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- --------------------------------------------------------

--
-- Table structure for table `theses`
--

CREATE TABLE `theses` (
  `id` int(11) NOT NULL,
  `title` varchar(255) NOT NULL,
  `author` varchar(255) DEFAULT NULL,
  `publication_year` year(4) DEFAULT NULL,
  `quantity` int(11) DEFAULT 0,
  `description` text DEFAULT NULL,
  `language` varchar(20) DEFAULT NULL,
  `degree` varchar(255) DEFAULT NULL,
  `institution` varchar(255) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Triggers `theses`
--
DELIMITER $$
CREATE TRIGGER `before_insert_theses` BEFORE INSERT ON `theses` FOR EACH ROW BEGIN
    -- Thêm bản ghi mới vào bảng Documents
    INSERT INTO Documents (title, author, quantity, description, language, publication_year)  
    VALUES (NEW.title, NEW.author, NEW.quantity, NEW.description, NEW.language, NEW.publication_year);
    -- Lấy ID của bản ghi vừa tạo trong bảng Documents
    SET NEW.id = LAST_INSERT_ID(); -- Đặt id của Theses bằng id vừa tạo của Documents
END
$$
DELIMITER ;

--
-- Indexes for dumped tables
--

--
-- Indexes for table `admin`
--
ALTER TABLE `admin`
  ADD PRIMARY KEY (`admin_id`),
  ADD UNIQUE KEY `email` (`email`);

--
-- Indexes for table `books`
--
ALTER TABLE `books`
  ADD PRIMARY KEY (`id`),
  ADD UNIQUE KEY `google_id` (`google_id`),
  ADD KEY `category_id` (`category_id`);

--
-- Indexes for table `borrow_records`
--
ALTER TABLE `borrow_records`
  ADD PRIMARY KEY (`record_id`),
  ADD KEY `document_id` (`document_id`),
  ADD KEY `member_id` (`member_id`);

--
-- Indexes for table `categories`
--
ALTER TABLE `categories`
  ADD PRIMARY KEY (`category_id`);

--
-- Indexes for table `documents`
--
ALTER TABLE `documents`
  ADD PRIMARY KEY (`id`),
  ADD UNIQUE KEY `google_id` (`google_id`);

--
-- Indexes for table `governmentdocuments`
--
ALTER TABLE `governmentdocuments`
  ADD PRIMARY KEY (`id`);

--
-- Indexes for table `members`
--
ALTER TABLE `members`
  ADD PRIMARY KEY (`member_id`),
  ADD UNIQUE KEY `email` (`email`);

--
-- Indexes for table `reviews`
--
ALTER TABLE `reviews`
  ADD PRIMARY KEY (`review_id`),
  ADD KEY `document_id` (`document_id`),
  ADD KEY `member_id` (`member_id`),
  ADD KEY `fk_book_id` (`book_id`);

--
-- Indexes for table `theses`
--
ALTER TABLE `theses`
  ADD PRIMARY KEY (`id`);

--
-- AUTO_INCREMENT for dumped tables
--

--
-- AUTO_INCREMENT for table `admin`
--
ALTER TABLE `admin`
  MODIFY `admin_id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=3;

--
-- AUTO_INCREMENT for table `books`
--
ALTER TABLE `books`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=161;

--
-- AUTO_INCREMENT for table `borrow_records`
--
ALTER TABLE `borrow_records`
  MODIFY `record_id` int(11) NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT for table `categories`
--
ALTER TABLE `categories`
  MODIFY `category_id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=6;

--
-- AUTO_INCREMENT for table `documents`
--
ALTER TABLE `documents`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=161;

--
-- AUTO_INCREMENT for table `members`
--
ALTER TABLE `members`
  MODIFY `member_id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=6;

--
-- AUTO_INCREMENT for table `reviews`
--
ALTER TABLE `reviews`
  MODIFY `review_id` int(11) NOT NULL AUTO_INCREMENT;

--
-- Constraints for dumped tables
--

--
-- Constraints for table `books`
--
ALTER TABLE `books`
  ADD CONSTRAINT `books_ibfk_1` FOREIGN KEY (`id`) REFERENCES `documents` (`id`) ON DELETE CASCADE ON UPDATE CASCADE,
  ADD CONSTRAINT `books_ibfk_2` FOREIGN KEY (`category_id`) REFERENCES `categories` (`category_id`) ON DELETE SET NULL ON UPDATE CASCADE;

--
-- Constraints for table `borrow_records`
--
ALTER TABLE `borrow_records`
  ADD CONSTRAINT `borrow_records_ibfk_1` FOREIGN KEY (`document_id`) REFERENCES `documents` (`id`) ON DELETE CASCADE ON UPDATE CASCADE,
  ADD CONSTRAINT `borrow_records_ibfk_2` FOREIGN KEY (`member_id`) REFERENCES `members` (`member_id`) ON DELETE CASCADE ON UPDATE CASCADE;

--
-- Constraints for table `governmentdocuments`
--
ALTER TABLE `governmentdocuments`
  ADD CONSTRAINT `governmentdocuments_ibfk_1` FOREIGN KEY (`id`) REFERENCES `documents` (`id`) ON DELETE CASCADE ON UPDATE CASCADE;

--
-- Constraints for table `reviews`
--
ALTER TABLE `reviews`
  ADD CONSTRAINT `fk_book_id` FOREIGN KEY (`book_id`) REFERENCES `books` (`id`) ON DELETE CASCADE,
  ADD CONSTRAINT `reviews_ibfk_1` FOREIGN KEY (`document_id`) REFERENCES `documents` (`id`) ON DELETE CASCADE ON UPDATE CASCADE,
  ADD CONSTRAINT `reviews_ibfk_2` FOREIGN KEY (`member_id`) REFERENCES `members` (`member_id`) ON DELETE CASCADE ON UPDATE CASCADE;

--
-- Constraints for table `theses`
--
ALTER TABLE `theses`
  ADD CONSTRAINT `theses_ibfk_1` FOREIGN KEY (`id`) REFERENCES `documents` (`id`) ON DELETE CASCADE ON UPDATE CASCADE;
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
