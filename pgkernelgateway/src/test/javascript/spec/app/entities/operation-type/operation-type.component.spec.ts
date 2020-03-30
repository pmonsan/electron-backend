/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { Observable, of } from 'rxjs';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { PgkernelgatewayTestModule } from '../../../test.module';
import { OperationTypeComponent } from 'app/entities/operation-type/operation-type.component';
import { OperationTypeService } from 'app/entities/operation-type/operation-type.service';
import { OperationType } from 'app/shared/model/operation-type.model';

describe('Component Tests', () => {
  describe('OperationType Management Component', () => {
    let comp: OperationTypeComponent;
    let fixture: ComponentFixture<OperationTypeComponent>;
    let service: OperationTypeService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [PgkernelgatewayTestModule],
        declarations: [OperationTypeComponent],
        providers: []
      })
        .overrideTemplate(OperationTypeComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(OperationTypeComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(OperationTypeService);
    });

    it('Should call load all on init', () => {
      // GIVEN
      const headers = new HttpHeaders().append('link', 'link;link');
      spyOn(service, 'query').and.returnValue(
        of(
          new HttpResponse({
            body: [new OperationType(123)],
            headers
          })
        )
      );

      // WHEN
      comp.ngOnInit();

      // THEN
      expect(service.query).toHaveBeenCalled();
      expect(comp.operationTypes[0]).toEqual(jasmine.objectContaining({ id: 123 }));
    });
  });
});
