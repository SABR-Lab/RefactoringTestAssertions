import chromadb
import json

# Disable telemetry to remove those error messages
import os
os.environ['ANONYMIZED_TELEMETRY'] = 'False'

# Load your Java analysis data
with open('/Users/jesusvaladez/Desktop/RefactoringMiner 2/test_method_evolution_for_ambari.json') as f:
   java_results = json.load(f)

chroma_client = chromadb.Client()
collection = chroma_client.create_collection(name="test_methods")

# Count how much data we're storing
total_methods = 0
for file_path, methods in java_results.items():
   for method_name, versions in methods.items():
       for i, version in enumerate(versions):
           collection.add(
               documents=[version['body']],
               ids=[f"{method_name}_{version['commit']}"],
               metadatas=[{
                   "method_name": method_name,
                   "file_path": file_path,
                   "commit": version['commit'],
                   "assertion_amount": version.get('assertion_amount', 0),
                   "assertion_type": version.get('assertion_type', ''),
                   "has_assertions": version.get('has_assertions', False)
               }]
           )
           total_methods += 1

print(f"Stored {total_methods} method versions from your Java analysis")

# Query your actual data
results = collection.query(
   query_texts=["assertEquals assertion test"],
   n_results=3
)

print("\nSearch results from your Java analysis:")
for i, doc in enumerate(results['documents'][0]):
   metadata = results['metadatas'][0][i]
   print(f"Method: {metadata['method_name']}")
   print(f"File: {metadata['file_path']}")
   print(f"Assertions: {metadata['assertion_amount']}")
   print(f"Code preview: {doc[:100]}...")
   print("---")

# Show methods with most assertions
results_with_assertions = collection.query(
   query_texts=["assert"],
   n_results=5,
   where={"has_assertions": True}
)

print(f"\nMethods with assertions:")
for i, doc in enumerate(results_with_assertions['documents'][0]):
   metadata = results_with_assertions['metadatas'][0][i]
   print(f"  {metadata['method_name']}: {metadata['assertion_amount']} assertions")