package univ.daws.service;

import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.container.ContainerResponseFilter;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.Provider;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import univ.daws.database.Connection;
import univ.daws.model.Product;

@Path("products")
@Produces(MediaType.APPLICATION_JSON)
public class ProductResource {

	Connection DB = new Connection("halas","halas00","daws");
	private List<Product> products = new ArrayList<Product>();
	
	public ProductResource() {
		DB.connect();
	}
	


	/**
	 * Return list of all products.
	 *
	 * @return JSON Response
	*/
	@GET
	public Response getProducts() {
		
		ResultSet data = null;
		try {
			data = DB.query("SELECT * FROM products");
			while(data.next()){
				products.add(new Product(data.getInt("id"), data.getString("name"), data.getInt("qty")));		
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return Response
				.status(Response.Status.OK)
				.entity(products)
				.build();
	}

	/**
	 * Return information of one product by id.
	 *
	 * @param  id      The id of product
	 * @return         JSON Response
	*/
	@GET
	@Path("{id}")
	public Response getProduct(@PathParam("id") int id) {
		

		ArrayList<Object> params = new ArrayList<>();
		params.add(id);
		
		ResultSet data;
		try {
			data = DB.preparedStatement("SELECT * FROM products where id = ?", params);
			data.next();
			Product oneProduct = new Product(data.getInt("id"), data.getString("name"), data.getInt("qty"));
			return Response
					.status(Response.Status.OK)
					.entity(oneProduct)
					.build();
		} catch (SQLException e) {
			return Response
					.status(Response.Status.INTERNAL_SERVER_ERROR)
					.entity(e)
					.build();
		}
		
	}

	/**
	 * Delete a specific product by id.
	 *
	 * @param  id      The id of product
	 * @return         JSON Response
	*/
	@DELETE
	@Path("{id}")
	public Response deleteProduct(@PathParam("id") int id) {
		try {
			
			int Deleted = DB.updateQuery("DELETE FROM products where id = "+ id +"");
			if (Deleted == 1) {
				 return Response
							.status(Response.Status.OK)
							.entity("Product Deleted Successfully")
							.build();
			} else {
				return Response
						.status(Response.Status.BAD_REQUEST)
						.entity("Cannot delete Product")
						.build();
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
			return Response
					.status(Response.Status.INTERNAL_SERVER_ERROR)
					.entity(e)
					.build();
		}
		
	}
	
	/**
	 * Add new product
	 *
	 * @param  product   The name and quantity of new product
	 * @return           JSON Response
	*/
	@POST
	@Path("create")
	@Consumes(MediaType.APPLICATION_JSON)
	public Response addProduct(Product newProduct) {
		try {
			String query = "INSERT INTO products (name, qty) VALUES ('"+newProduct.getName().toString()+"',"+newProduct.getQuantity()+")";
			int created = DB.updateQuery(query);
			if (created == 1) {
				return Response
						.status(Response.Status.OK)
						.entity("Product created successfully")
						.build();
			}else {
				return Response
						.status(Response.Status.BAD_REQUEST)
						.entity("Cannot Create Product")
						.build();
			}
		} catch (SQLException e) {
			e.printStackTrace();
			return Response
					.status(Response.Status.INTERNAL_SERVER_ERROR)
					.entity(e)
					.build();
		}
		
		
	}
	
	/**
	 * Update a specific product by id
	 *
	 * @param  product   The id, name and quantity of product
	 * @return           JSON Response
	*/
	@PUT
	@Path("update")
	@Consumes(MediaType.APPLICATION_JSON)
	public Response updateProduct(Product updateProduct) {
		try {
			int isUpdated = DB.updateQuery("UPDATE products set name = '"+ updateProduct.getName()+"',qty = "+ updateProduct.getQuantity() +" WHERE id = "+updateProduct.getId()+"");
			if (isUpdated == 1) {
				return Response
						.status(Response.Status.OK)
						.entity("Product Updated successfully")
						.build();
			}else {
				return Response
						.status(Response.Status.BAD_REQUEST)
						.entity("Cannot Update Product")
						.build();
			}
		} catch (SQLException e) {
			e.printStackTrace();
			return Response
					.status(Response.Status.INTERNAL_SERVER_ERROR)
					.entity(e)
					.build();
		}
	}
}
