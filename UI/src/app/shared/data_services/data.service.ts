import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders} from '@angular/common/http';

import { Observable, of } from 'rxjs';
import { Observer } from 'rxjs';
import { catchError, map, tap } from 'rxjs/operators';
import { Student } from '../student';
import { ConfigService } from '../api_settings/config.service';
import { MessageService } from '../message_service/message.service';

@Injectable({
  providedIn: 'root'
})
export class DataService {

  private headers = new HttpHeaders({'Content-Type': 'application/json'});

  _baseUrl: string = '';

  constructor(private http: HttpClient,
              private configService: ConfigService,
              private messageService: MessageService) {
    this._baseUrl = configService.getApiURI();
  }

  getStudents(): Observable<Student[]> {
    return this.http.get<Student[]>(this._baseUrl + '/student')
      .pipe(
        tap(_ => this.log('fetched student')),
        catchError(this.handleError<Student[]>('getStudent', []))
      );
  }

  handleUpload(formData) {
    this.http.post<any>(this._baseUrl + '/upload', formData).subscribe(
      (res) => console.log(res),
      (err) => console.log(err)
    );
  }

  /**
   * Handle Http operation that failed.
   * Let the app continue.
   * @param operation - name of the operation that failed
   * @param result - optional value to return as the observable result
   */
  private handleError<T>(operation = 'operation', result?: T) {
    return (error: any): Observable<T> => {

      // TODO: send the error to remote logging infrastructure
      console.error(error); // log to console instead

      // TODO: better job of transforming error for user consumption
      this.log(`${operation} failed: ${error.message}`);

      // Let the app keep running by returning an empty result.
      return of(result as T);
    };
  }

  /** Log a HeroService message with the MessageService */
  private log(message: string) {
    this.messageService.add(`HeroService: ${message}`);
  }
}
