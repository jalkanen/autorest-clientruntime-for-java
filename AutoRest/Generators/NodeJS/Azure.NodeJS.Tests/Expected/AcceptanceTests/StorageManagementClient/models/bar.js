/*
 * Copyright (c) Microsoft Corporation. All rights reserved.
 * Licensed under the MIT License. See License.txt in the project root for
 * license information.
 * 
 * Code generated by Microsoft (R) AutoRest Code Generator.
 * Changes may cause incorrect behavior and will be lost if the code is
 * regenerated.
 */

'use strict';

var models = require('./index');

/**
 * @class
 * Initializes a new instance of the Bar class.
 * @constructor
 * The URIs that are used to perform a retrieval of a public blob, queue or
 * table object.
 * @member {object} [recursivePoint] Recursive Endpoints
 * 
 * @member {string} [recursivePoint.blob] Gets the blob endpoint.
 * 
 * @member {string} [recursivePoint.queue] Gets the queue endpoint.
 * 
 * @member {string} [recursivePoint.table] Gets the table endpoint.
 * 
 * @member {object} [recursivePoint.dummyEndPoint] Dummy EndPoint
 * 
 * @member {object} [recursivePoint.fooPoint] Foo point
 * 
 * @member {object} [recursivePoint.fooPoint.barPoint] Bar point
 * 
 */
function Bar() {
}

/**
 * Defines the metadata of Bar
 *
 * @returns {object} metadata of Bar
 *
 */
Bar.prototype.mapper = function () {
  return {
    required: false,
    serializedName: 'Bar',
    type: {
      name: 'Composite',
      className: 'Bar',
      modelProperties: {
        recursivePoint: {
          required: false,
          serializedName: 'RecursivePoint',
          type: {
            name: 'Composite',
            className: 'Endpoints'
          }
        }
      }
    }
  };
};

module.exports = Bar;
