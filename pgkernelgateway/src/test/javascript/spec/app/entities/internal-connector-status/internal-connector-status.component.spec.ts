/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { Observable, of } from 'rxjs';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { PgkernelgatewayTestModule } from '../../../test.module';
import { InternalConnectorStatusComponent } from 'app/entities/internal-connector-status/internal-connector-status.component';
import { InternalConnectorStatusService } from 'app/entities/internal-connector-status/internal-connector-status.service';
import { InternalConnectorStatus } from 'app/shared/model/internal-connector-status.model';

describe('Component Tests', () => {
  describe('InternalConnectorStatus Management Component', () => {
    let comp: InternalConnectorStatusComponent;
    let fixture: ComponentFixture<InternalConnectorStatusComponent>;
    let service: InternalConnectorStatusService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [PgkernelgatewayTestModule],
        declarations: [InternalConnectorStatusComponent],
        providers: []
      })
        .overrideTemplate(InternalConnectorStatusComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(InternalConnectorStatusComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(InternalConnectorStatusService);
    });

    it('Should call load all on init', () => {
      // GIVEN
      const headers = new HttpHeaders().append('link', 'link;link');
      spyOn(service, 'query').and.returnValue(
        of(
          new HttpResponse({
            body: [new InternalConnectorStatus(123)],
            headers
          })
        )
      );

      // WHEN
      comp.ngOnInit();

      // THEN
      expect(service.query).toHaveBeenCalled();
      expect(comp.internalConnectorStatuses[0]).toEqual(jasmine.objectContaining({ id: 123 }));
    });
  });
});
