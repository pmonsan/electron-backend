/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { Observable, of } from 'rxjs';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { PgkernelgatewayTestModule } from '../../../test.module';
import { InternalConnectorRequestComponent } from 'app/entities/internal-connector-request/internal-connector-request.component';
import { InternalConnectorRequestService } from 'app/entities/internal-connector-request/internal-connector-request.service';
import { InternalConnectorRequest } from 'app/shared/model/internal-connector-request.model';

describe('Component Tests', () => {
  describe('InternalConnectorRequest Management Component', () => {
    let comp: InternalConnectorRequestComponent;
    let fixture: ComponentFixture<InternalConnectorRequestComponent>;
    let service: InternalConnectorRequestService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [PgkernelgatewayTestModule],
        declarations: [InternalConnectorRequestComponent],
        providers: []
      })
        .overrideTemplate(InternalConnectorRequestComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(InternalConnectorRequestComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(InternalConnectorRequestService);
    });

    it('Should call load all on init', () => {
      // GIVEN
      const headers = new HttpHeaders().append('link', 'link;link');
      spyOn(service, 'query').and.returnValue(
        of(
          new HttpResponse({
            body: [new InternalConnectorRequest(123)],
            headers
          })
        )
      );

      // WHEN
      comp.ngOnInit();

      // THEN
      expect(service.query).toHaveBeenCalled();
      expect(comp.internalConnectorRequests[0]).toEqual(jasmine.objectContaining({ id: 123 }));
    });
  });
});
