USE [master]
GO
/****** Object:  Database [qrem]    Script Date: 28/10/2022 04:55:43 p. m. ******/
CREATE DATABASE [qrem]
 CONTAINMENT = NONE
 ON  PRIMARY 
( NAME = N'qrem', FILENAME = N'C:\Program Files\Microsoft SQL Server\MSSQL15.MSSQLSERVER\MSSQL\DATA\qrem.mdf' , SIZE = 8192KB , MAXSIZE = UNLIMITED, FILEGROWTH = 65536KB )
 LOG ON 
( NAME = N'qrem_log', FILENAME = N'C:\Program Files\Microsoft SQL Server\MSSQL15.MSSQLSERVER\MSSQL\DATA\qrem_log.ldf' , SIZE = 8192KB , MAXSIZE = 2048GB , FILEGROWTH = 65536KB )
 WITH CATALOG_COLLATION = DATABASE_DEFAULT
GO
ALTER DATABASE [qrem] SET COMPATIBILITY_LEVEL = 150
GO
IF (1 = FULLTEXTSERVICEPROPERTY('IsFullTextInstalled'))
begin
EXEC [qrem].[dbo].[sp_fulltext_database] @action = 'enable'
end
GO
ALTER DATABASE [qrem] SET ANSI_NULL_DEFAULT OFF 
GO
ALTER DATABASE [qrem] SET ANSI_NULLS OFF 
GO
ALTER DATABASE [qrem] SET ANSI_PADDING OFF 
GO
ALTER DATABASE [qrem] SET ANSI_WARNINGS OFF 
GO
ALTER DATABASE [qrem] SET ARITHABORT OFF 
GO
ALTER DATABASE [qrem] SET AUTO_CLOSE ON 
GO
ALTER DATABASE [qrem] SET AUTO_SHRINK OFF 
GO
ALTER DATABASE [qrem] SET AUTO_UPDATE_STATISTICS ON 
GO
ALTER DATABASE [qrem] SET CURSOR_CLOSE_ON_COMMIT OFF 
GO
ALTER DATABASE [qrem] SET CURSOR_DEFAULT  GLOBAL 
GO
ALTER DATABASE [qrem] SET CONCAT_NULL_YIELDS_NULL OFF 
GO
ALTER DATABASE [qrem] SET NUMERIC_ROUNDABORT OFF 
GO
ALTER DATABASE [qrem] SET QUOTED_IDENTIFIER OFF 
GO
ALTER DATABASE [qrem] SET RECURSIVE_TRIGGERS OFF 
GO
ALTER DATABASE [qrem] SET  ENABLE_BROKER 
GO
ALTER DATABASE [qrem] SET AUTO_UPDATE_STATISTICS_ASYNC OFF 
GO
ALTER DATABASE [qrem] SET DATE_CORRELATION_OPTIMIZATION OFF 
GO
ALTER DATABASE [qrem] SET TRUSTWORTHY OFF 
GO
ALTER DATABASE [qrem] SET ALLOW_SNAPSHOT_ISOLATION OFF 
GO
ALTER DATABASE [qrem] SET PARAMETERIZATION SIMPLE 
GO
ALTER DATABASE [qrem] SET READ_COMMITTED_SNAPSHOT OFF 
GO
ALTER DATABASE [qrem] SET HONOR_BROKER_PRIORITY OFF 
GO
ALTER DATABASE [qrem] SET RECOVERY SIMPLE 
GO
ALTER DATABASE [qrem] SET  MULTI_USER 
GO
ALTER DATABASE [qrem] SET PAGE_VERIFY CHECKSUM  
GO
ALTER DATABASE [qrem] SET DB_CHAINING OFF 
GO
ALTER DATABASE [qrem] SET FILESTREAM( NON_TRANSACTED_ACCESS = OFF ) 
GO
ALTER DATABASE [qrem] SET TARGET_RECOVERY_TIME = 60 SECONDS 
GO
ALTER DATABASE [qrem] SET DELAYED_DURABILITY = DISABLED 
GO
ALTER DATABASE [qrem] SET ACCELERATED_DATABASE_RECOVERY = OFF  
GO
ALTER DATABASE [qrem] SET QUERY_STORE = OFF
GO
USE [qrem]
GO
/****** Object:  Table [dbo].[paciente]    Script Date: 28/10/2022 04:55:44 p. m. ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[paciente](
	[id] [int] IDENTITY(1,1) NOT NULL,
	[nombre] [varchar](20) NOT NULL,
	[apellidoPaterno] [varchar](20) NOT NULL,
	[apellidoMaterno] [varchar](20) NOT NULL,
	[pass] [varchar](50) NOT NULL,
	[edad] [tinyint] NOT NULL,
	[peso] [tinyint] NOT NULL,
	[tipoSangre] [char](2) NOT NULL,
	[alergias] [varchar](150) NOT NULL,
	[seguro] [varchar](50) NOT NULL,
	[contacto] [varchar](10) NOT NULL,
PRIMARY KEY CLUSTERED 
(
	[id] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
) ON [PRIMARY]
GO
/****** Object:  Table [dbo].[reporte]    Script Date: 28/10/2022 04:55:44 p. m. ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[reporte](
	[fecha] [date] NOT NULL,
	[descripcion] [varchar](200) NOT NULL,
	[cedulaParamedico] [char](12) NOT NULL,
	[idPaciente] [int] NULL
) ON [PRIMARY]
GO
/****** Object:  View [dbo].[bitacora]    Script Date: 28/10/2022 04:55:44 p. m. ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
create view [dbo].[bitacora] as
	select r.cedulaParamedico, r.descripcion, convert(varchar, r.fecha, 3) as fecha, r.idPaciente, CONCAT(p.nombre,' ',p.apellidoPaterno,' ', p.apellidoMaterno) as paciente from reporte r 
	inner join paciente p 
	on p.id=r.idPaciente
GO
/****** Object:  Table [dbo].[paramedico]    Script Date: 28/10/2022 04:55:44 p. m. ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[paramedico](
	[nombre] [varchar](20) NOT NULL,
	[apellidoPaterno] [varchar](20) NOT NULL,
	[apellidoMaterno] [varchar](20) NOT NULL,
	[pass] [varchar](50) NOT NULL,
	[cedula] [char](12) NOT NULL,
PRIMARY KEY CLUSTERED 
(
	[cedula] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
) ON [PRIMARY]
GO
ALTER TABLE [dbo].[reporte]  WITH CHECK ADD FOREIGN KEY([cedulaParamedico])
REFERENCES [dbo].[paramedico] ([cedula])
GO
ALTER TABLE [dbo].[reporte]  WITH CHECK ADD FOREIGN KEY([idPaciente])
REFERENCES [dbo].[paciente] ([id])
GO
USE [master]
GO
ALTER DATABASE [qrem] SET  READ_WRITE 
GO
