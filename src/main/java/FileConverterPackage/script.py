import sys
import os
import docx2pdf
from pdf2docx import Converter


def convert_pdf_to_docx(file_path, destination_path):
    cv = Converter(file_path)
    cv.convert(destination_path, start=0, end=None)
    cv.close()


def convert_docx_to_pdf(file_path, destination_path):
    docx2pdf.convert(file_path, destination_path)


def convert_file(file_path, destination_path, conversion_type):
    if conversion_type == "pdf_to_docx":
        convert_pdf_to_docx(file_path, destination_path)
        print("PDF to DOCX conversion completed.")
    elif conversion_type == "docx_to_pdf":
        convert_docx_to_pdf(file_path, destination_path)
        print("DOCX to PDF conversion completed.")
    else:
        print("Invalid conversion type.")


if __name__ == "__main__":
    file_path = sys.argv[1]
    destination_path = sys.argv[2]
    conversion_type = sys.argv[3]

    if os.path.isfile(file_path):
        file_extension = os.path.splitext(file_path)[1]

        if conversion_type == "pdf_to_docx" and file_extension == ".pdf":
            convert_file(file_path, destination_path, conversion_type)
        elif conversion_type == "docx_to_pdf" and file_extension == ".docx":
            convert_file(file_path, destination_path, conversion_type)
        else:
            print("Invalid file extension for the chosen conversion type.")
    else:
        print("File not found.")
