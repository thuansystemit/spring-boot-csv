import { Injectable } from '@angular/core';

@Injectable({
  providedIn: 'root'
})
export class ConfigService {
  _apiURI : string;

  constructor() {
    //This should be changed accourding to API Url.
    this._apiURI = 'http://localhost:8080/api/v1';
  }

  getApiURI() {
    return this._apiURI;
  }

  getApiHost() {
    return this._apiURI.replace('api/','');
  }

}
