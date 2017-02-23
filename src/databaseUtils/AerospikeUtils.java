package databaseUtils;

import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;

import com.aerospike.client.AerospikeClient;
import com.aerospike.client.AerospikeException;
import com.aerospike.client.Bin;
import com.aerospike.client.Key;
import com.aerospike.client.Record;
import com.aerospike.client.ResultCode;
import com.aerospike.client.ScanCallback;
import com.aerospike.client.lua.LuaAerospikeLib.log;
import com.aerospike.client.policy.ClientPolicy;
import com.aerospike.client.policy.ScanPolicy;
import com.aerospike.client.policy.WritePolicy;

public class AerospikeUtils extends Thread {
	private String username;
	private String password;
	private int timeout;
	private String host;
	private int port;
	private int count = 0;
	private ClientPolicy clientpolicy;
	private AerospikeClient client;

	public AerospikeUtils(String username, String password, int timeout,
			String host, int port) {
		this.username = username;
		this.password = password;
		this.timeout = timeout;
		this.host = host;
		this.port = port;

		this.clientpolicy = getClientPolicy(username, password, timeout);
		this.client = createClient(clientpolicy, host, port);

	}

	public AerospikeClient createClient(ClientPolicy clientPolicy, String host,
			int port) {
		AerospikeClient client = null;
		client = new AerospikeClient(clientPolicy, host, port);
		return client;
	}

	public ClientPolicy getClientPolicy(String username, String password,
			int timeout) {
		ClientPolicy clientPolicy = new ClientPolicy();
		clientPolicy.user = username;
		clientPolicy.password = password;
		clientPolicy.timeout = timeout;

		return clientPolicy;
	}

	public void Aerospikefinally(AerospikeClient client) {

		if (client != null && client.isConnected()) {
			System.out.println("Closed");
			client.close();
		} else
			System.out.println("closed");

	}

	public void aerospikeConnectionCheck() {
		ClientPolicy cp = getClientPolicy(username, password, timeout);
		System.out.println("connecting to aerospike");
		System.out.println("Thread Name- " + Thread.currentThread().getName());
		AerospikeClient ac = createClient(cp, host, port);
		if (ac.isConnected()) {
			System.out.println("Connected");
		}
		Aerospikefinally(ac);

	}

	public void run() {
		// Creating ClientPolicy
		ClientPolicy cp = getClientPolicy(username, password, timeout);
		System.out.println("connecting to aerospike");
		System.out.println(Thread.currentThread().getName());
		AerospikeClient ac = createClient(cp, host, port);
		if (ac.isConnected()) {
			System.out.println("Connected");
			// readaero(ac);
			delete_ps_cache(ac);

		}
		Aerospikefinally(ac);

	}

	/*
	 * static AerospikeClient client; static AerospikeClient client2; public
	 * static void main(String arg[]){ ClientPolicy clientPolicy=new
	 * ClientPolicy(); clientPolicy.user ="mtzapp"; clientPolicy.password
	 * ="helium"; clientPolicy.timeout=3000; try{
	 * 
	 * //10.41.57.105 //52.76.199.171
	 * 
	 * 
	 * client=new AerospikeClient(clientPolicy,"52.76.199.171",3000);
	 * client2=new AerospikeClient(clientPolicy,"52.76.159.132",3000); //Record
	 * record=client.get(policy, keys) if(client.isConnected()){
	 * System.out.println("Client 1 Connected"); getdata();
	 * deleteall_data(client,"monetization-cache","subcat_seller_constraint");
	 * deleteall_data(client, "monetization-cache", "ps-rules-subcategory");
	 * deleteall_data(client, "monetization-cache", "ps-rules-brand");
	 * deleteall_data(client, "monetization-cache", "ps-rules-cluster");
	 * deleteall_data(client, "monetization-cache",
	 * "ps-rules-cluster-brand-map"); deleteall_data(client,
	 * "monetization-cache", "ps-rules-cluster-supc-map");
	 * deleteall_data(client, "monetization-cache",
	 * "ps-rules-cluster-vendor-map"); deleteall_data(client,
	 * "monetization-cache", "ps-rules-exception"); deleteall_data(client,
	 * "monetization-cache", "ps-rules-bulkupload" ); //deleteall_data(client,
	 * "monetization-cache", "ps-rules-ocp"); } if(client2.isConnected()){
	 * System.out.println("Client 2 Connected");
	 * deleteall_data(client2,"monetization-cache","subcat_seller_constraint");
	 * deleteall_data(client2, "monetization-cache", "ps-rules-subcategory");
	 * deleteall_data(client2, "monetization-cache", "ps-rules-brand");
	 * deleteall_data(client2, "monetization-cache", "ps-rules-cluster");
	 * deleteall_data(client2, "monetization-cache",
	 * "ps-rules-cluster-brand-map"); deleteall_data(client2,
	 * "monetization-cache", "ps-rules-cluster-supc-map");
	 * deleteall_data(client2, "monetization-cache",
	 * "ps-rules-cluster-vendor-map"); deleteall_data(client2,
	 * "monetization-cache", "ps-rules-exception"); deleteall_data(client2,
	 * "monetization-cache", "ps-rules-bulkupload" ); deleteall_data(client2,
	 * "monetization-cache", "ps-rules-ocp");
	 * 
	 * }
	 * 
	 * else { System.out.println("Not connected"); }
	 * 
	 * } catch(Exception e){ System.out.println(e);
	 * 
	 * } finally { if (client != null && client.isConnected()) { } // Close
	 * Aerospike server connection
	 * 
	 * client.close(); } if (client2 != null && client2.isConnected()) { } //
	 * Close Aerospike server connection
	 * 
	 * client.close(); }
	 */

	public void getdatafromAeroPersistance(String namespace, String setname,
			String pk, String field) {

		Record userRecord = null;
		Key userKey = null;
		// Check if username exists
		userKey = new Key(namespace, setname, pk);
		userRecord = client.get(null, userKey);
		if (userRecord != null) {

			System.out.println("PK::" + pk + "::::" + field + "::VALUE::"
					+ userRecord.getValue(field));

		} else {
			System.out.println("ERROR: record not found!\n");
		}

	}

	public AerospikeClient getAerospikeConnection() {
		ClientPolicy cp = getClientPolicy(username, password, timeout);
		System.out.println("connecting to aerospike");
		System.out.println(Thread.currentThread().getName());
		AerospikeClient ac = createClient(cp, host, port);
		return ac;
	}

	public void updateAerospikePersistance(AerospikeClient ac,
			String namespace, String setname, String pk, Bin... bin) {
		if (ac.isConnected()) {

			System.out.println("Connected");
			Key key = new Key(namespace, setname, pk);
			System.out.println("Updating Key" + pk);
			try {
				ac.put(new WritePolicy(), key, bin);
			} catch (Exception e) {
				System.out.println("Unable to Update Aero");
				e.printStackTrace();

			}
			System.out.println("Record Updated");
		} else
			System.out.println("NOT CONNECTED");
	}

	public void deleteSpecific(String namespace, String setname, String pk) {
		ClientPolicy cp = getClientPolicy(username, password, timeout);
		System.out.println("connecting to aerospike");
		System.out.println(Thread.currentThread().getName());
		AerospikeClient ac = createClient(cp, host, port);
		if (ac.isConnected()) {

			System.out.println("Connected");
			Key key = new Key(namespace, setname, pk);
			System.out.println("Deleting the Key" + pk);
			boolean z = ac.delete(new WritePolicy(), key);
			System.out.println("Is record deleted- " + z);
		}
		Aerospikefinally(ac);

	}

	public void deleteall_data(final AerospikeClient client, String namespace,
			String set) {

		try {
			// final AerospikeClient client = new AerospikeClient(host, port);

			ScanPolicy scanPolicy = new ScanPolicy();
			/*
			 * scan the entire Set using scannAll(). This will scan each node in
			 * the cluster and return the record Digest to the call back object
			 */
			client.scanAll(scanPolicy, namespace, set, new ScanCallback() {

				public void scanCallback(Key key, Record record)
						throws AerospikeException {
					/*
					 * for each Digest returned, delete it using delete()
					 */
					client.delete(new WritePolicy(), key);
					count++;
					/*
					 * after 25,000 records delete, return print the count.
					 */
					if (count % 500 == 0) {
						System.out.println("Deleted " + count);
					}
				}
			}, new String[] {});
			System.out.println("Deleted " + count + " records from set " + set
					+ "  --" + Thread.currentThread().getName());
		} catch (AerospikeException e) {
			int resultCode = e.getResultCode();
			System.out.println(ResultCode.getResultString(resultCode));
			System.out.println("Error details: " + e);
		}

	}

	public void delete_ps_cache(AerospikeClient client) {
		//deleteall_data(client, "monetization-cache", "ps-rules-bulkupload-new");
		
		
		deleteall_data(client, "monetization-cache", "ps_kafka_active_supc_set");
		
		
	//deleteall_data(client, "monetization-cache", "ps-rfs-list");

	/*	 deleteall_data(client,"monetization-cache","subcat_seller_constraint");

	deleteall_data(client, "monetization-cache", "ps-rules-subcategory");
		deleteall_data(client, "monetization-cache", "ps-rules-brand");
		deleteall_data(client, "monetization-cache", "ps-rules-cluster");
		deleteall_data(client, "monetization-cache",
				"ps-rules-cluster-brand-map");
		deleteall_data(client, "monetization-cache",
				"ps-rules-cluster-supc-map");
		deleteall_data(client, "monetization-cache",
				"ps-rules-cluster-vendor-map");
		deleteall_data(client, "monetization-cache", "ps-rules-exception");
		
		deleteall_data(client, "monetization-cache", "ps-rules-ocp");*/
		/*		 * deleteall_data(client,"monetization-cache","seller_list");
		 * deleteall_data
		 * (client,"monetization-cache","subcat_seller_constraint");
		 * deleteall_data(client,"monetization-cache","ta_seller_list");
		 * deleteall_data(client,"monetization-cache","default_constraints");
		 * deleteall_data(client,"monetization-cache","big-seller");
		 * deleteall_data(client,"monetization-cache","marketing_fee_flag");
		 * deleteall_data(client,"monetization-cache","ps-active-subcats");
		 * deleteall_data(client,"monetization-cache","auto-soi-benfit");
		 * deleteall_data(client,"monetization-cache","subcat-configuration");
		 * deleteall_data(client,"monetization-cache","ps-active-sp-subcats");
		 */

	}

	public void readaero(AerospikeClient aero) {
		readDataFromAero(aero, "monetization-cache", "ps-rfs-list");
	}

	public void readDataFromAero(final AerospikeClient client,
			String namespace, String set) {
		// final AerospikeClient client = new AerospikeClient(host, port);

		final ScanPolicy scanPolicy = new ScanPolicy();
		/*
		 * scan the entire Set using scannAll(). This will scan each node in the
		 * cluster and return the record Digest to the call back object
		 */
		client.scanAll(scanPolicy, namespace, set, new ScanCallback() {

			public void scanCallback(Key key, Record record)
					throws AerospikeException {
				/*
				 * for each Digest returned, delete it using delete()
				 */

				Record record1 = client.get(scanPolicy, key);
				Map<String, Object> obj = record1.bins;

				// Record recorder = client.get(new WritePolicy(),key);
				// Map<String,Object> maps=recorder.;
				for (Entry<String, Object> entry : obj.entrySet()) {
					System.out.println(entry.getKey() + "::::"
							+ entry.getValue().toString());
				}

			}
		}, new String[] {});

		/*
		 * public void scanCallback(Key key, Record record) throws
		 * AerospikeException { try { Record record1 = client.get(scanPolicy,
		 * key); Map<String, Object> obj=record1.bins;
		 * 
		 * } catch (Exception e) { // TODO Auto-generated catch block
		 * e.printStackTrace(); } } }, new String[] {});
		 */

	}
}
