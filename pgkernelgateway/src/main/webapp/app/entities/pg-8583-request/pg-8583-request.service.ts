import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import * as moment from 'moment';
import { DATE_FORMAT } from 'app/shared/constants/input.constants';
import { map } from 'rxjs/operators';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared';
import { IPg8583Request } from 'app/shared/model/pg-8583-request.model';

type EntityResponseType = HttpResponse<IPg8583Request>;
type EntityArrayResponseType = HttpResponse<IPg8583Request[]>;

@Injectable({ providedIn: 'root' })
export class Pg8583RequestService {
  public resourceUrl = SERVER_API_URL + 'api/pg-8583-requests';

  constructor(protected http: HttpClient) {}

  create(pg8583Request: IPg8583Request): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(pg8583Request);
    return this.http
      .post<IPg8583Request>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  update(pg8583Request: IPg8583Request): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(pg8583Request);
    return this.http
      .put<IPg8583Request>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http
      .get<IPg8583Request>(`${this.resourceUrl}/${id}`, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<IPg8583Request[]>(this.resourceUrl, { params: options, observe: 'response' })
      .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
  }

  delete(id: number): Observable<HttpResponse<any>> {
    return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  protected convertDateFromClient(pg8583Request: IPg8583Request): IPg8583Request {
    const copy: IPg8583Request = Object.assign({}, pg8583Request, {
      registrationDate:
        pg8583Request.registrationDate != null && pg8583Request.registrationDate.isValid() ? pg8583Request.registrationDate.toJSON() : null,
      responseDate: pg8583Request.responseDate != null && pg8583Request.responseDate.isValid() ? pg8583Request.responseDate.toJSON() : null
    });
    return copy;
  }

  protected convertDateFromServer(res: EntityResponseType): EntityResponseType {
    if (res.body) {
      res.body.registrationDate = res.body.registrationDate != null ? moment(res.body.registrationDate) : null;
      res.body.responseDate = res.body.responseDate != null ? moment(res.body.responseDate) : null;
    }
    return res;
  }

  protected convertDateArrayFromServer(res: EntityArrayResponseType): EntityArrayResponseType {
    if (res.body) {
      res.body.forEach((pg8583Request: IPg8583Request) => {
        pg8583Request.registrationDate = pg8583Request.registrationDate != null ? moment(pg8583Request.registrationDate) : null;
        pg8583Request.responseDate = pg8583Request.responseDate != null ? moment(pg8583Request.responseDate) : null;
      });
    }
    return res;
  }
}
