/* tslint:disable max-line-length */
import { TestBed, getTestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { of } from 'rxjs';
import { take, map } from 'rxjs/operators';
import { AgentService } from 'app/entities/agent/agent.service';
import { IAgent, Agent } from 'app/shared/model/agent.model';

describe('Service Tests', () => {
  describe('Agent Service', () => {
    let injector: TestBed;
    let service: AgentService;
    let httpMock: HttpTestingController;
    let elemDefault: IAgent;
    let expectedResult;
    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HttpClientTestingModule]
      });
      expectedResult = {};
      injector = getTestBed();
      service = injector.get(AgentService);
      httpMock = injector.get(HttpTestingController);

      elemDefault = new Agent(
        0,
        'AAAAAAA',
        'AAAAAAA',
        'AAAAAAA',
        'AAAAAAA',
        'AAAAAAA',
        'AAAAAAA',
        'AAAAAAA',
        'AAAAAAA',
        'AAAAAAA',
        'AAAAAAA',
        'AAAAAAA',
        'AAAAAAA',
        'AAAAAAA',
        'AAAAAAA',
        false
      );
    });

    describe('Service methods', () => {
      it('should find an element', async () => {
        const returnedFromService = Object.assign({}, elemDefault);
        service
          .find(123)
          .pipe(take(1))
          .subscribe(resp => (expectedResult = resp));

        const req = httpMock.expectOne({ method: 'GET' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject({ body: elemDefault });
      });

      it('should create a Agent', async () => {
        const returnedFromService = Object.assign(
          {
            id: 0
          },
          elemDefault
        );
        const expected = Object.assign({}, returnedFromService);
        service
          .create(new Agent(null))
          .pipe(take(1))
          .subscribe(resp => (expectedResult = resp));
        const req = httpMock.expectOne({ method: 'POST' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject({ body: expected });
      });

      it('should update a Agent', async () => {
        const returnedFromService = Object.assign(
          {
            matricule: 'BBBBBB',
            firstname: 'BBBBBB',
            lastname: 'BBBBBB',
            birthdate: 'BBBBBB',
            email: 'BBBBBB',
            mobilePhone: 'BBBBBB',
            businessEmail: 'BBBBBB',
            businessPhone: 'BBBBBB',
            address: 'BBBBBB',
            postalCode: 'BBBBBB',
            city: 'BBBBBB',
            countryCode: 'BBBBBB',
            partnerCode: 'BBBBBB',
            username: 'BBBBBB',
            active: true
          },
          elemDefault
        );

        const expected = Object.assign({}, returnedFromService);
        service
          .update(expected)
          .pipe(take(1))
          .subscribe(resp => (expectedResult = resp));
        const req = httpMock.expectOne({ method: 'PUT' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject({ body: expected });
      });

      it('should return a list of Agent', async () => {
        const returnedFromService = Object.assign(
          {
            matricule: 'BBBBBB',
            firstname: 'BBBBBB',
            lastname: 'BBBBBB',
            birthdate: 'BBBBBB',
            email: 'BBBBBB',
            mobilePhone: 'BBBBBB',
            businessEmail: 'BBBBBB',
            businessPhone: 'BBBBBB',
            address: 'BBBBBB',
            postalCode: 'BBBBBB',
            city: 'BBBBBB',
            countryCode: 'BBBBBB',
            partnerCode: 'BBBBBB',
            username: 'BBBBBB',
            active: true
          },
          elemDefault
        );
        const expected = Object.assign({}, returnedFromService);
        service
          .query(expected)
          .pipe(
            take(1),
            map(resp => resp.body)
          )
          .subscribe(body => (expectedResult = body));
        const req = httpMock.expectOne({ method: 'GET' });
        req.flush([returnedFromService]);
        httpMock.verify();
        expect(expectedResult).toContainEqual(expected);
      });

      it('should delete a Agent', async () => {
        const rxPromise = service.delete(123).subscribe(resp => (expectedResult = resp.ok));

        const req = httpMock.expectOne({ method: 'DELETE' });
        req.flush({ status: 200 });
        expect(expectedResult);
      });
    });

    afterEach(() => {
      httpMock.verify();
    });
  });
});
