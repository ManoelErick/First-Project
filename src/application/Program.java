package application;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Locale;
import java.util.Scanner;
import java.util.stream.Collectors;

import entities.Products;

public class Program {

	public static void main(String[] args) {

		Locale.setDefault(Locale.US);
		Scanner input = new Scanner(System.in);

		System.out.print("Enter the path file: ");
		String path = input.nextLine();

		try (BufferedReader br = new BufferedReader(new FileReader(path))) {

			List<Products> list = new ArrayList<>();

			String line = br.readLine();
			while (line != null) {

				String[] vect = line.split(",");

				list.add(new Products(vect[0], Double.parseDouble(vect[1])));

				line = br.readLine();

			}

			double avg = list.stream().map(p -> p.getPrice()).reduce(0.0, (x, y) -> x + y) / list.size();

			System.out.println("Average price: " + String.format("%.2f", avg));
			System.out.println("------------------");
			
			Comparator<String> comp = (x1, y1) -> x1.toUpperCase().compareTo(y1.toUpperCase());

			List<String> names = list.stream()
					.filter(p1 -> p1.getPrice() < avg)
					.map(p2 -> p2.getName())
					.sorted(comp.reversed())
					.collect(Collectors.toList());

			System.out.println("Items: ");
			names.forEach(System.out::println);

		} catch (Exception e) {
			System.out.println("Error: " + e.getMessage());
		}

		input.close();
	}

}
