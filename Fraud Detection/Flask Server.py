from flask import Flask, request, jsonify
import joblib
import numpy as np
import pickle

# Load your trained model
model = joblib.load('detection2.pkl')

# Load means and scales from the file
with open('means_scales.pkl', 'rb') as f:
    means, scales = pickle.load(f)

app = Flask(__name__)

@app.route('/predict', methods=['POST'])
def predict():
    data = request.get_json()
    
    # Check if all required features are present in the received JSON data
    required_features = ['amount', 'oldbalanceOrg', 'newbalanceOrig', 'oldbalanceDest', 'newbalanceDest']
    for feature in required_features:
        if feature not in data:
            return jsonify({'error': f'Missing key "{feature}" in JSON data'}), 400
    
    # Extract features from JSON data
    org = data['amount']
    dest = data['oldbalanceOrg']
    back = data['newbalanceOrig']
    up = data['oldbalanceDest']
    head = data['newbalanceDest']
    
    # Scale the input data
    scaled_data = [(org - means[0]) / scales[0],
                   (dest - means[1]) / scales[1],
                   (back - means[2]) / scales[2],
                   (up - means[3]) / scales[3],
                   (head - means[4]) / scales[4]]
    
    # Make predictions using the loaded model
    predictions = model.predict([scaled_data])

    return jsonify({'predictions': predictions.tolist()})

if __name__ == '__main__':
    app.run(debug=True, port=5000)
