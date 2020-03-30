/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { Observable, of } from 'rxjs';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { PgkernelgatewayTestModule } from '../../../test.module';
import { OperationStatusComponent } from 'app/entities/operation-status/operation-status.component';
import { OperationStatusService } from 'app/entities/operation-status/operation-status.service';
import { OperationStatus } from 'app/shared/model/operation-status.model';

describe('Component Tests', () => {
  describe('OperationStatus Management Component', () => {
    let comp: OperationStatusComponent;
    let fixture: ComponentFixture<OperationStatusComponent>;
    let service: OperationStatusService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [PgkernelgatewayTestModule],
        declarations: [OperationStatusComponent],
        providers: []
      })
        .overrideTemplate(OperationStatusComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(OperationStatusComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(OperationStatusService);
    });

    it('Should call load all on init', () => {
      // GIVEN
      const headers = new HttpHeaders().append('link', 'link;link');
      spyOn(service, 'query').and.returnValue(
        of(
          new HttpResponse({
            body: [new OperationStatus(123)],
            headers
          })
        )
      );

      // WHEN
      comp.ngOnInit();

      // THEN
      expect(service.query).toHaveBeenCalled();
      expect(comp.operationStatuses[0]).toEqual(jasmine.objectContaining({ id: 123 }));
    });
  });
});
