/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { Observable, of } from 'rxjs';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { PgkernelgatewayTestModule } from '../../../test.module';
import { OperationComponent } from 'app/entities/operation/operation.component';
import { OperationService } from 'app/entities/operation/operation.service';
import { Operation } from 'app/shared/model/operation.model';

describe('Component Tests', () => {
  describe('Operation Management Component', () => {
    let comp: OperationComponent;
    let fixture: ComponentFixture<OperationComponent>;
    let service: OperationService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [PgkernelgatewayTestModule],
        declarations: [OperationComponent],
        providers: []
      })
        .overrideTemplate(OperationComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(OperationComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(OperationService);
    });

    it('Should call load all on init', () => {
      // GIVEN
      const headers = new HttpHeaders().append('link', 'link;link');
      spyOn(service, 'query').and.returnValue(
        of(
          new HttpResponse({
            body: [new Operation(123)],
            headers
          })
        )
      );

      // WHEN
      comp.ngOnInit();

      // THEN
      expect(service.query).toHaveBeenCalled();
      expect(comp.operations[0]).toEqual(jasmine.objectContaining({ id: 123 }));
    });
  });
});
