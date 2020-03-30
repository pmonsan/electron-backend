import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import * as moment from 'moment';
import { DATE_FORMAT } from 'app/shared/constants/input.constants';
import { map } from 'rxjs/operators';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared';
import { IInternalConnectorRequest } from 'app/shared/model/internal-connector-request.model';

type EntityResponseType = HttpResponse<IInternalConnectorRequest>;
type EntityArrayResponseType = HttpResponse<IInternalConnectorRequest[]>;

@Injectable({ providedIn: 'root' })
export class InternalConnectorRequestService {
  public resourceUrl = SERVER_API_URL + 'api/internal-connector-requests';

  constructor(protected http: HttpClient) {}

  create(internalConnectorRequest: IInternalConnectorRequest): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(internalConnectorRequest);
    return this.http
      .post<IInternalConnectorRequest>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  update(internalConnectorRequest: IInternalConnectorRequest): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(internalConnectorRequest);
    return this.http
      .put<IInternalConnectorRequest>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http
      .get<IInternalConnectorRequest>(`${this.resourceUrl}/${id}`, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<IInternalConnectorRequest[]>(this.resourceUrl, { params: options, observe: 'response' })
      .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
  }

  delete(id: number): Observable<HttpResponse<any>> {
    return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  protected convertDateFromClient(internalConnectorRequest: IInternalConnectorRequest): IInternalConnectorRequest {
    const copy: IInternalConnectorRequest = Object.assign({}, internalConnectorRequest, {
      registrationDate:
        internalConnectorRequest.registrationDate != null && internalConnectorRequest.registrationDate.isValid()
          ? internalConnectorRequest.registrationDate.toJSON()
          : null,
      responseDate:
        internalConnectorRequest.responseDate != null && internalConnectorRequest.responseDate.isValid()
          ? internalConnectorRequest.responseDate.toJSON()
          : null
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
      res.body.forEach((internalConnectorRequest: IInternalConnectorRequest) => {
        internalConnectorRequest.registrationDate =
          internalConnectorRequest.registrationDate != null ? moment(internalConnectorRequest.registrationDate) : null;
        internalConnectorRequest.responseDate =
          internalConnectorRequest.responseDate != null ? moment(internalConnectorRequest.responseDate) : null;
      });
    }
    return res;
  }
}
